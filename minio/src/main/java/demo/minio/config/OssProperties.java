package demo.minio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Data;

/**
 * OssProperties
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/7/23
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * 开关
     */
    private boolean enabled;
    /**
     * oss 类型
     */
    private String type;
    @NestedConfigurationProperty
    private final MinioProperties minio = new MinioProperties();
}
