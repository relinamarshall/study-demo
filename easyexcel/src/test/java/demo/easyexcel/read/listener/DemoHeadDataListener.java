package demo.easyexcel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import demo.easyexcel.read.dto.DemoDto;

/**
 * DemoHeadDataListener
 *
 * @author Wenzhou
 * @since 2023/3/18 2:18
 */
public class DemoHeadDataListener implements ReadListener<DemoDto> {
    /**
     * BATCH_COUNT
     */
    private static final int BATCH_COUNT = 5;
    /**
     * cachedDataList
     */
    private List<DemoDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * invokeHead
     * 这里会一行行的返回头
     *
     * @param headMap Map
     * @param context AnalysisContext
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        System.out.println("解析到一条头数据:" + JSON.toJSONString(headMap));
        // 如果想转成 Map<Integer,String>
        // 方案1： 不要implements ReadListener 而是 extends AnalysisEventListener
        // 方案2： 调用 ConverterUtils.convertToStringMap(headMap,context) 自动会转换
    }

    @Override
    public void invoke(DemoDto demoDto, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据:" + JSON.toJSONString(demoDto));
        cachedDataList.add(demoDto);
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
     * 加上存储数据库
     */
    private void saveData() {
        System.out.println(cachedDataList.size() + "条数据，开始存储数据库!");
        System.out.println("存储数据库成功!");
    }
}