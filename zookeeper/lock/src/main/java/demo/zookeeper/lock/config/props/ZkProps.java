package demo.zookeeper.lock.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * ZkProps
 * <p>
 * Zookeeper 配置项
 *
 * @author Wenzhou
 * @since 2023/3/28 17:19
 */
@Data
@ConfigurationProperties(prefix = "zk")
public class ZkProps {
    /**
     * 连接地址
     */
    private String url;

    /**
     * 超时时间(毫秒)，默认1000
     */
    private int timeout = 1000;

    /**
     * 重试次数，默认3
     */
    private int retry = 3;
}
