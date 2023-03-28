package demo.kafka.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * KafkaConsumerService
 *
 * @author Wenzhou
 * @since 2023/3/28 10:23
 */
@Slf4j
@Service
public class KafkaConsumerService {
    /**
     * 消费单条消息,topics 可以监听多个topic 如：topics={"topic1","topic2"}
     *
     * @param message 消息
     */
    @KafkaListener(id = "Single", topics = "kafka-single-topic")
    public void consumerSingle(String message) {
        log.info("Single ===> message:{}", message);
    }

    /**
     * 批量消费消息
     *
     * @param messages
     */
    @KafkaListener(id = "Batch", topics = "kafka-batch-topic",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumerBatch(List<ConsumerRecord<String, String>> messages) {
        log.info("Batch ===> size: {}", messages.size());
        messages.forEach(item->{
            log.info(item.value());
        });
    }

    /**
     * 指定消费异常处理器
     *
     * @param message
     */
    @KafkaListener(id = "Exception", topics = "kafka-exception-topic",
            errorHandler = "consumerAwareListenerErrorHandler")
    public void consumerException(String message) {
        log.info("Exception ===> message:{}", message);
        throw new RuntimeException("consumer exception");
    }


    /**
     * 验证ConsumerInterceptor
     *
     * @param message
     */
    @KafkaListener(id = "Interceptor", topics = "kafka-interceptor-topic")
    public void consumerInterceptor(String message) {
        log.info("Interceptor ===> message:{}", message);
    }
}
