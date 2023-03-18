package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * DemoMergeDto
 *
 * @author Wenzhou
 * @since 2023/3/17 15:57
 */
@Getter
@Setter
@EqualsAndHashCode
// 将第6-7行的2-3列合并成一个单元格
@OnceAbsoluteMerge(firstRowIndex = 5, lastRowIndex = 6, firstColumnIndex = 1, lastColumnIndex = 2)
public class DemoMergeDto {
    /**
     * string
     * 这一列 每隔2行 合并单元格
     */
    @ContentLoopMerge(eachRow = 2)
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
