package demo.hazelcast.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import demo.hazelcast.listener.TopicListener;

/**
 * HazelcastConfig
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/15
 */
@Configuration
@ConditionalOnProperty(name = "hazelcast.enabled", havingValue = "true")
@EnableConfigurationProperties(HazelcastProperties.class)
public class HazelcastConfig {
    /**
     * HAZELCAST_TOPIC
     */
    public static final String HAZELCAST_TOPIC = "test";

    @Bean
    public ClientConfig config(HazelcastProperties properties) {
        ClientConfig config = new ClientConfig();
        ClientNetworkConfig network = config.getNetworkConfig();

        config.setClusterName(properties.getName());
        String[] members = properties.getMembers().split(",");
        Arrays.stream(members).forEach(network::addAddress);
        return config;
    }

    @Bean(name = "HazelcastClient")
    public HazelcastInstance hazelcastInstance(ClientConfig config) {
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        // 发布/订阅模式
        ITopic<String> topic = client.getTopic(HAZELCAST_TOPIC);
        // 新增topic的监听者，具体实现加后文
        topic.addMessageListener(new TopicListener());
        return client;
    }
}
