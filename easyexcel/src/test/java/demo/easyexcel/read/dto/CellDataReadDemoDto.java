package demo.easyexcel.read.dto;

import com.alibaba.excel.metadata.data.CellData;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * CellDataReadDemoDto
 *
 * @author Wenzhou
 * @since 2023/3/18 2:12
 */
@Getter
@Setter
@EqualsAndHashCode
public class CellDataReadDemoDto {
    /**
     * string
     */
    private CellData<String> string;
    /**
     * date
     * 这里注意 虽然是日期 但是类型 存储的number 是因为excel 存储的就是number
     */
    private CellData<Date> date;
    /**
     * doubleData
     */
    private CellData<Double> doubleData;
    /**
     * formulaValue
     * 这里并不一定能完美的获取 有些公式是 依赖性的 可能会读取不到 这个问题后续会修复
     */
    private CellData<String> formulaValue;
}
