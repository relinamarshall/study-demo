package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * IndexDto
 *
 * @author Wenzhou
 * @since 2023/3/17 17:37
 */
@Getter
@Setter
@EqualsAndHashCode
public class IndexDto {
    /**
     * string
     */
    @ExcelProperty(value = "字符串标题\n（选填）", index = 0)
    private String string;
    /**
     * date
     */
    @ExcelProperty(value = "日期标题\n（选填）", index = 1)
    private Date date;
    /**
     * doubleData
     * 这里设置3 会导致第二列空的
     */
    @ExcelProperty(value = "数字标题", index = 3)
    private Double doubleData;
}
