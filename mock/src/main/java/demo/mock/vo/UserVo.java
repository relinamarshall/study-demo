package demo.mock.vo;

import lombok.Builder;
import lombok.Data;

/**
 * UserVo
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@Data
@Builder
public class UserVo {
    private String name;
    private String address;
}
