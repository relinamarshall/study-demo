package demo.hazelcast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

import lombok.Data;

/**
 * HazelcastProperties
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/15
 */
@Data
@ConfigurationProperties(prefix = "hazelcast", ignoreInvalidFields = true)
public class HazelcastProperties implements Serializable {
    /**
     * 是否开启 hazelcast，默认：true
     */
    private boolean enabled = true;
    /**
     * 集群名称
     */
    private String name = "dev";
    /**
     * 静态配置，多个使用逗号隔开
     */
    private String members;
}
