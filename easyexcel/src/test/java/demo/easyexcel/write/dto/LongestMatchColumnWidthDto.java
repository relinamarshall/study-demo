package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * LongestMatchColumnWidthDto
 *
 * @author Wenzhou
 * @since 2023/3/17 17:38
 */
@Getter
@Setter
@EqualsAndHashCode
public class LongestMatchColumnWidthDto {
    /**
     * string
     */
    @ExcelProperty("字符串标题")
    private String string;
    /**
     * date
     */
    @ExcelProperty("日期标题很长日期标题很长日期标题很长很长")
    private Date date;
    /**
     * doubleData
     */
    @ExcelProperty("数字")
    private Double doubleData;
}
