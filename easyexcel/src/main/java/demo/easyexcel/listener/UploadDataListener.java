package demo.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;

import java.util.List;

import demo.easyexcel.dao.UploadDao;
import demo.easyexcel.dto.UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UploadDataListener
 *
 * @author Wenzhou
 * @since 2023/3/18 2:59
 */
@Slf4j
@RequiredArgsConstructor
public class UploadDataListener implements ReadListener<UploadDto> {
    /**
     * BATCH_COUNT
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    /**
     * cachedDataList
     */
    List<UploadDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * uploadDao
     * 假设这个是一个DAO 当然有业务逻辑这个也可以是一个service  当然如果不用存储这个对象没用
     */
    private final UploadDao uploadDao;

    /**
     * invoke
     * 这个每一条数据解析都会调用
     *
     * @param data            UploadDto
     * @param analysisContext AnalysisContext
     */
    @Override
    public void invoke(UploadDto data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * doAfterAllAnalysed
     * 所有数据解析完成后调用
     *
     * @param analysisContext AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成!");
    }

    /**
     * saveData
     */
    private void saveData() {
        log.info("{}条数据,开始存储数据库!", cachedDataList.size());
        uploadDao.save(cachedDataList);
        log.info("存储数据库成功!");
    }
}
