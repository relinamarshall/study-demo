package demo.apollo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * autowired custom configuration
 *
 * @author Wenzhou
 * @since 2023/3/12 18:41
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ConfigProperties {
    /**
     * name
     */
    @Value("${name}")
    private String name;
    /**
     * mysqlHost
     */
    @Value("${mysql.host}")
    private String mysqlHost;
    /**
     * mysqlPort
     */
    @Value("${mysql.port}")
    private String mysqlPort;
    /**
     * mysqlUsername
     */
    @Value("${mysql.username}")
    private String mysqlUsername;
    /**
     * mysqlPassword
     */
    @Value("${mysql.password}")
    private String mysqlPassword;
}
