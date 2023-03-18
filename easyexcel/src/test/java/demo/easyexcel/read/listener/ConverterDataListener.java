package demo.easyexcel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;

import java.util.List;

import demo.easyexcel.read.dto.ConverterDto;

/**
 * ConverterDataListener
 *
 * @author Wenzhou
 * @since 2023/3/18 2:28
 */
public class ConverterDataListener implements ReadListener<ConverterDto> {
    /**
     * BATCH_COUNT
     */
    private static final int BATCH_COUNT = 5;
    /**
     * cachedDataList
     */
    private List<ConverterDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(ConverterDto data, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:" + JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        System.out.println("所有数据解析完成!");
    }

    /**
     * saveData
     */
    private void saveData() {
        System.out.println(cachedDataList.size() + "条数据,开始存储数据库!");
        System.out.println("存储数据库成功!");
    }
}
