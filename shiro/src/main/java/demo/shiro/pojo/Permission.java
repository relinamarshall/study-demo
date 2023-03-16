package demo.shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Permission
 *
 * @author Wenzhou
 * @since 2023/3/16 20:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    /**
     * id
     */
    private Integer id;
    /**
     * code
     */
    private String code;
    /**
     * description
     */
    private String description;
    /**
     * url
     */
    private String url;
}
