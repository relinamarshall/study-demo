package demo.easyexcel.read.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;

import demo.easyexcel.read.converter.CustomStringStringConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * ConverterDto
 *
 * @author Wenzhou
 * @since 2023/3/18 2:11
 */
@Getter
@Setter
@EqualsAndHashCode
public class ConverterDto {
    /**
     * string
     * 自定义 转换器 不管数据库传过来什么  都加上'自定义:'
     */
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;
    /**
     * date
     * 用String 去接日期才能格式化 想接收年月日格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String date;
    /**
     * doubleData
     * 接受百分比的数字
     */
    @NumberFormat("#.##")
    private String doubleData;
}
