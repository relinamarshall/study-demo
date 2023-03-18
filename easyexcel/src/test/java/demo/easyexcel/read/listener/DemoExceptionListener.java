package demo.easyexcel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import demo.easyexcel.read.dto.ExceptionDemoDto;

/**
 * DemoExceptionListener
 *
 * @author Wenzhou
 * @since 2023/3/18 2:23
 */
public class DemoExceptionListener implements ReadListener<ExceptionDemoDto> {
    /**
     * BATCH_COUNT
     */
    private static final int BATCH_COUNT = 5;
    /**
     * cachedDataList
     */
    private List<ExceptionDemoDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 在转换异常 获取其他异常下会调用本接口 抛出异常则停止读取 如果这里不抛出异常 则继续读取下一行
     *
     * @param exception Exception
     * @param context   AnalysisContext
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        System.out.println("解析失败，但是继续解析下一行" + exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            System.out.println("第" + excelDataConvertException.getRowIndex()
                    + "行,第" + excelDataConvertException.getColumnIndex()
                    + "列解析异常,数据为:" + excelDataConvertException.getCellData());
        }
    }

    /**
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
    public void invoke(ExceptionDemoDto data, AnalysisContext analysisContext) {
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
     * 加上存储数据库
     */
    private void saveData() {
        System.out.println(cachedDataList.size() + "条数据，开始存储数据库!");
        System.out.println("存储数据库成功!");
    }
}
