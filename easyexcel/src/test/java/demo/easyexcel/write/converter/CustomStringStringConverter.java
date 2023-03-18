package demo.easyexcel.write.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * CustomStringStringConverter
 *
 * @author Wenzhou
 * @since 2023/3/17 11:36
 */
public class CustomStringStringConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * convertToJavaData
     * 这里读的时候会调用
     *
     * @param context ReadConverterContext
     * @return String
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return "自定义: " + context.getReadCellData().getStringValue();
    }

    /**
     * convertToExcelData
     * 这里是写的时候会调用 不用管
     *
     * @param context WriteConverterContext
     * @return WriteCellData
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        return new WriteCellData<>("自定义: " + context.getValue());
    }
}
