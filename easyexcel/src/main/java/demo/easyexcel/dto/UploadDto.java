package demo.easyexcel.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * UploadDto
 *
 * @author Wenzhou
 * @since 2023/3/18 3:00
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UploadDto {
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
    private Double doubleData;
}
