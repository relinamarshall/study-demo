package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * ComplexHeadDto
 *
 * @author Wenzhou
 * @since 2023/3/17 15:33
 */
@Getter
@Setter
@EqualsAndHashCode
public class ComplexHeadDto {
    /**
     * string
     */
    @ExcelProperty({"主标题", "字符串标题"})
    private String string;
    /**
     * date
     */
    @ExcelProperty({"主标题", "副标题", "日期标题"})
    private Date date;
    /**
     * doubleData
     */
    @ExcelProperty({"主标题", "副标题", "数字标题"})
    private Double doubleData;
}
