package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;

import java.util.Date;

import demo.easyexcel.write.converter.CustomStringStringConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * ConverterDto
 *
 * @author Wenzhou
 * @since 2023/3/17 15:31
 */
@Getter
@Setter
@EqualsAndHashCode
public class ConverterDto {
    /**
     * string
     * <p>
     * 所有的字符串起前面加上”自定义：“三个字
     */
    @ExcelProperty(value = "字符串标题", converter = CustomStringStringConverter.class)
    private String string;

    /**
     * date
     * <p>
     * excel 用年月日的格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty("日期标题")
    private Date date;

    /**
     * doubleData
     * <p>
     * excel用百分比表示
     */
    @NumberFormat("#,##0.00%")
    @ExcelProperty("数字标题")
    private Double doubleData;
}
