package demo.easyexcel.fill.dto;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * FillDto
 *
 * @author Wenzhou
 * @since 2023/3/17 11:31
 */
@Getter
@Setter
@EqualsAndHashCode
public class FillDto {
    /**
     * name
     */
    private String name;
    /**
     * number
     */
    private double number;
    /**
     * date
     */
    private Date date;
}
