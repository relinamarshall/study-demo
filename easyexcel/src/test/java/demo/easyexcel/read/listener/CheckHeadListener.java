package demo.easyexcel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSONObject;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import demo.easyexcel.read.dto.ImportResultVo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CheckHeadListener
 *
 * @author Wenzhou
 * @since 2023/7/4 9:53
 */
@Slf4j
@NoArgsConstructor
public class CheckHeadListener<T> extends AnalysisEventListener<T> {
    private String[] headTemplate;
    private List<T> successData;
    private List<T> failData;
    private Integer total = 0;
    private Integer successCnt = 0;
    private Integer failCnt = 0;
    private Predicate<T> validate;
    private Predicate<List<T>> save;
    private RuntimeException exception;

    private static final int BATCH_COUNT = 1000;
    private List<T> cachedList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public CheckHeadListener(String[] headTemplate, Predicate<T> validate, Predicate<List<T>> save) {
        this(headTemplate, validate, save, new RuntimeException("导入失败,导入文件非模板文件"));
    }

    public CheckHeadListener(String[] headTemplate, Predicate<T> validate,
                             Predicate<List<T>> save, RuntimeException e) {
        this.headTemplate = headTemplate;
        this.validate = validate;
        this.save = save;
        this.exception = e;
        this.successData = new ArrayList<>();
        this.failData = new ArrayList<>();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (headMap.size() != headTemplate.length) {
            headError();
            return;
        }
        for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
            Integer k = entry.getKey();
            if (!headTemplate[k].equals(headMap.get(k).trim())) {
                headError();
                return;
            }
        }
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        total++;
        if (validate.test(t)) {
            successCnt++;
            successData.add(t);
        } else {
            failCnt++;
            failData.add(t);
        }

        if (cachedList.size() >= BATCH_COUNT) {
            Assert.isTrue(save.test(cachedList), "保存失败,请重试!");
            cachedList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (!successData.isEmpty()) {
            log.debug("[预保存数据大小-{}-{}]: {}", total, successCnt, JSONObject.toJSONString(successData));
        }
        if (!failData.isEmpty()) {
            log.debug("[预失败数据大小-{}-{}]: {}", total, failCnt, JSONObject.toJSONString(failData));
        }
        if (!cachedList.isEmpty()) {
            Assert.isTrue(save.test(cachedList), "保存失败,请重试!");
        }
    }

    private void headError() {
        throw exception;
    }

    public ImportResultVo<T> buildImportResultVo() {
        return ImportResultVo.<T>builder()
                .total(total)
                .failNum(failCnt)
                .failList(failData)
                .successNum(successCnt)
                .successList(successData)
                .build();
    }
}
