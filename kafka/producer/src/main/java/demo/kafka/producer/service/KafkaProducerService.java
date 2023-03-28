package demo.kafka.producer.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * KafkaProducerService
 *
 * @author Wenzhou
 * @since 2023/3/28 10:08
 */
@Slf4j
@Service
public class KafkaProducerService {
    /**
     * kafkaTemplate
     */
    @Resource
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;
    /**
     * kafkaTemplateWithTransaction
     */
    @Resource
    @Qualifier("kafkaTemplateWithTransaction")
    private KafkaTemplate<String, String> kafkaTemplateWithTransaction;

    /**
     * sendMessageSync
     * <p>
     * 发送消息(同步)
     *
     * @param topic   主题
     * @param key     键
     * @param message 值
     */
    @SneakyThrows
    public void sendMessageSync(String topic, String key, String message) {
        // 可以指定最长等待时间 也可以不指定
        kafkaTemplate.send(topic, message).get(10, TimeUnit.SECONDS);
        log.info("sendMessageSync => topic: {},key: {},message: {}", topic, key, message);
    }

    /**
     * sendMessageSyncForKey
     * <p>
     * 指定key kafka 根据key进行hash 决定存入哪个partition
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    @SneakyThrows
    public void sendMessageSyncForKey(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message).get(10, TimeUnit.SECONDS);
        log.info("sendMessageSyncForKey => topic: {},key: {},message: {}", topic, key, message);
    }

    /**
     * sendMessageSyncForPartition
     * <p>
     * 指定分区
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    @SneakyThrows
    public void sendMessageSyncForPartition(String topic, String key, String message) {
        // 存入指定partition
        kafkaTemplate.send(topic, 0, key, message).get(10, TimeUnit.SECONDS);
        log.info("sendMessageSyncForPartition => topic: {},key: {},message: {}", topic, key, message);

    }

    /**
     * sendMessageGetResult
     * <p>
     * 发送消息并获取结果
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    @SneakyThrows
    public void sendMessageGetResult(String topic, String key, String message) {
        SendResult<String, String> result = kafkaTemplate.send(topic, message).get();
        log.info("sendMessageGetResult => topic: {},key: {},message: {},result: {}",
                topic, key, message, result.toString());
    }

    /**
     * sendMessageAsync
     * <p>
     * 发送消息(异步)
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    public void sendMessageAsync(String topic, String key, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        // 添加回调
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("sendMessageAsync failure ==> topic: {},message: {}", topic, message);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("sendMessageAsync success ==> topic: {},message: {},result: {}",
                        topic, message, result.toString());
            }
        });
    }

    /**
     * testMessageBuilderMessage
     * <p>
     * 可以将消息组装成 Message 对象
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    @SneakyThrows
    public void testMessageBuilderMessage(String topic, String key, String message) {
        // 组装消息
        Message<String> msg = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.PREFIX, "kafka_")
                .build();
        // 同步发送
        kafkaTemplate.send(msg).get();
    }

    /**
     * testMessageBuilderProducerRecord
     * <p>
     * 可以将消息组装成 ProducerRecord对象 发送
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    @SneakyThrows
    public void testMessageBuilderProducerRecord(String topic, String key, String message) {
        // 组装消息
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, message);
        kafkaTemplate.send(producerRecord).get(10, TimeUnit.SECONDS);
    }

    /**
     * sendMessageInTransaction
     * <p>
     * 以事务方式发送消息
     *
     * @param topic   String
     * @param key     String
     * @param message String
     */
    public void sendMessageInTransaction(String topic, String key, String message) {
        kafkaTemplateWithTransaction.executeInTransaction(kafkaOperations -> {
            kafkaOperations.send(topic, key, message);
            // 出现异常将会中断事务,消息不会发送出去
            throw new RuntimeException("exception");
        });
    }
}
