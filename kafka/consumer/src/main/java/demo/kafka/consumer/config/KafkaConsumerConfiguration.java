package demo.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

import java.util.HashMap;
import java.util.Map;

import demo.kafka.consumer.interceptor.DiyConsumerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * KafkaConsumerConfiguration
 *
 * @author Wenzhou
 * @since 2023/3/28 10:21
 */
@Slf4j
@Configuration
public class KafkaConsumerConfiguration {
    /**
     * consumerFactory
     *
     * @return ConsumerFactory
     */
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        //kafka集群地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "yifan4:9092,yifan5:9092,yifan6:9092");
        //自动提交 offset 默认 true
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //自动提交的频率 单位 ms
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        //批量消费最大数量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        //消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        //session超时，超过这个时间consumer没有发送心跳,就会触发rebalance操作
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 120000);
        //请求超时
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 120000);
        //Key 反序列化类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //Value 反序列化类
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //当kafka中没有初始offset或offset超出范围时将自动重置offset
        //earliest:重置为分区中最小的offset
        //latest:重置为分区中最新的offset(消费分区中新产生的数据)
        //none:只要有一个分区不存在已提交的offset,就抛出异常
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //设置Consumer拦截器
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, DiyConsumerInterceptor.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * kafkaListenerContainerFactory
     *
     * @return KafkaListenerContainerFactory
     */
    @Bean("kafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 设置 consumerFactory
        factory.setConsumerFactory(consumerFactory());
        // 设置是否开启批量监听 测试批量处理时需要改为true
        factory.setBatchListener(true);
        // 设置消费者组中的线程和数量
        factory.setConcurrency(5);
        return factory;
    }

    /**
     * consumerAwareBatchErrorHandler
     *
     * @return ConsumerAwareListenerErrorHandler
     */
    @Bean("consumerAwareListenerErrorHandler")
    public ConsumerAwareListenerErrorHandler consumerAwareBatchErrorHandler() {
        return (message, e, consumer) -> {
            // 打印消费异常的消息和异常信息
            log.info("consumer failed! message:{},exceptionMsg:{},groupId:{}", message, e.getMessage(), e.getGroupId());
            return null;
        };
    }
}
