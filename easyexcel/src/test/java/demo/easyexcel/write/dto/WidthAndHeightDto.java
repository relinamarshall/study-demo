package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * WidthAndHeightDto
 *
 * @author Wenzhou
 * @since 2023/3/17 17:39
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class WidthAndHeightDto {
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
     * 宽度为50
     */
    @ColumnWidth(50)
    @ExcelProperty("数字标题")
    private Double doubleData;
}
