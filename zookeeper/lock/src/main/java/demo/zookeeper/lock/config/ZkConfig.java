package demo.zookeeper.lock.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.zookeeper.lock.config.props.ZkProps;

/**
 * ZkConfig
 * <p>
 * Zookeeper配置类
 *
 * @author Wenzhou
 * @since 2023/3/28 17:20
 */
@Configuration
@EnableConfigurationProperties(ZkProps.class)
public class ZkConfig {
    /**
     * zkProps
     */
    private final ZkProps zkProps;

    /**
     * ZkConfig
     *
     * @param zkProps ZkProps
     */
    @Autowired
    public ZkConfig(ZkProps zkProps) {
        this.zkProps = zkProps;
    }

    /**
     * curatorFramework
     *
     * @return CuratorFramework
     */
    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkProps.getTimeout(), zkProps.getRetry());
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkProps.getUrl(), retryPolicy);
        client.start();
        return client;
    }
}
