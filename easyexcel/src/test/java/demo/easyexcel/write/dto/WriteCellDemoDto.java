package demo.easyexcel.write.dto;

import com.alibaba.excel.metadata.data.WriteCellData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * WriteCellDemoDto
 *
 * @author Wenzhou
 * @since 2023/3/17 17:42
 */
@Getter
@Setter
@EqualsAndHashCode
public class WriteCellDemoDto {
    /**
     * hyperlink
     * 超链接
     */
    private WriteCellData<String> hyperlink;
    /**
     * commentData
     * 备注
     */
    private WriteCellData<String> commentData;
    /**
     * formulaData
     * 公式
     */
    private WriteCellData<String> formulaData;
    /**
     * writeCellStyle
     * 指定单元格的样式 当然样式也可以用注解等方式
     */
    private WriteCellData<String> writeCellStyle;
    /**
     * richText
     * 指定一个单元格有多个样式
     */
    private WriteCellData<String> richText;
}
