package demo.shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 *
 * @author Wenzhou
 * @since 2023/3/16 20:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * id
     */
    private Integer id;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * fullname
     */
    private String fullname;
    /**
     * mobile
     */
    private String mobile;
}
