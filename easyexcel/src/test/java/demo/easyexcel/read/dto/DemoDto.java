package demo.easyexcel.read.dto;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * DemoDto
 *
 * @author Wenzhou
 * @since 2023/3/18 2:10
 */
@Getter
@Setter
@EqualsAndHashCode
public class DemoDto {
    /**
     * string
     */
    private String string;
    /**
     * date
     */
    private Date date;
    /**
     * doubleData
     */
    Double doubleData;
}
