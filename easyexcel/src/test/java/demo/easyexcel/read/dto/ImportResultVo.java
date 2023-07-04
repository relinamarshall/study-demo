package demo.easyexcel.read.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ImportResultVo
 *
 * @author Wenzhou
 * @since 2023/7/4 16:28
 */
@Data
@Builder
@EqualsAndHashCode
public class ImportResultVo<T> {
    private Integer failNum;
    private Integer successNum;
    private Integer total;
    private List<T> failList;
    private List<T> successList;
}
