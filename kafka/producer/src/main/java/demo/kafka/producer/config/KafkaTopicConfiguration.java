package demo.kafka.producer.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaTopicConfiguration
 *
 * @author Wenzhou
 * @since 2023/3/28 10:14
 */
@Configuration
public class KafkaTopicConfiguration {
    /**
     * serverAddress
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String serverAddress;

    /**
     * 创建KafkaAdmin 可以自动检测集群中是否存在topic 不存在则创建
     *
     * @return KafkaAdmin
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        return new KafkaAdmin(props);
    }

    /**
     * newTopic
     *
     * @return NewTopic
     */
    @Bean
    public NewTopic newTopic() {
        // 创建 topic 指定 名称 分区数 副本数
        return new NewTopic("hello-kafka-test-topic", 1, (short) 1);
    }
}
