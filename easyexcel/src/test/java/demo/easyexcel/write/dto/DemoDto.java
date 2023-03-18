package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * DemoDto
 *
 * @author Wenzhou
 * @since 2023/3/17 15:56
 */
@Getter
@Setter
@EqualsAndHashCode
public class DemoDto {
    /**
     * string
     */
    @ExcelProperty(value = "字符串标题")
    private String string;
    /**
     * date
     */
    @ExcelProperty(value = "日期标题")
    private Date date;
    /**
     * doubleData
     */
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * ignore
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
