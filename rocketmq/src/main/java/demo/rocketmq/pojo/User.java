package demo.rocketmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 *
 * @author Wenzhou
 * @since 2023/5/4 16:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * name
     */
    private String name;
    /**
     * address
     */
    private String address;
}
