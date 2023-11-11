package demo.dingtalk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * DingTalkConfig
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/11/11
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkConfig {
    /**
     * 机器人群对应的webhook
     */
    private String webhook;
    /**
     * 机器人群中的加签
     */
    private String secret;
}
