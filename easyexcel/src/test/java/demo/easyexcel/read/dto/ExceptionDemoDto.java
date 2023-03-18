package demo.easyexcel.read.dto;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * ExceptionDemoDto
 *
 * @author Wenzhou
 * @since 2023/3/18 2:15
 */
@Getter
@Setter
@EqualsAndHashCode
public class ExceptionDemoDto {
    /**
     * date
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}
