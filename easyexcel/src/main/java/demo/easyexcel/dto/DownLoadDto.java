package demo.easyexcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DownLoadDto
 *
 * @author Wenzhou
 * @since 2023/3/18 3:06
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DownLoadDto {
    /**
     * string
     */
    @ExcelProperty("字符串标题")
    private String string;
    /**
     * date
     */
    @ExcelProperty("日期标题")
    private Date date;
    /**
     * doubleData
     */
    @ExcelProperty("数字标题")
    private Double doubleData;
}
