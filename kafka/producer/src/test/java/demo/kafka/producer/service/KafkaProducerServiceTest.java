package demo.kafka.producer.service;

import com.alibaba.fastjson.JSON;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import demo.kafka.producer.ProducerApplicationTest;
import demo.kafka.producer.entity.User;
import lombok.extern.slf4j.Slf4j;

/**
 * KafkaProducerServiceTest
 *
 * @author Wenzhou
 * @since 2023/3/28 10:43
 */
@Slf4j
public class KafkaProducerServiceTest extends ProducerApplicationTest {
    /**
     * kafkaProducerService
     */
    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * sendMessageSync
     * <p>
     * 发送消息(同步)
     */
    @Test
    public void sendMessageSync() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.sendMessageSync(topic, key, message);
    }

    /**
     * sendMessageSyncForKey
     * <p>
     * 指定key kafka 根据key进行hash 决定存入哪个partition
     */
    @Test
    public void sendMessageSyncForKey() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.sendMessageSyncForKey(topic, key, message);
    }

    /**
     * sendMessageSyncForPartition
     * <p>
     * 指定分区
     */
    @Test
    public void sendMessageSyncForPartition() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.sendMessageSyncForPartition(topic, key, message);
    }

    /**
     * testSendMessageGetResult
     * <p>
     * 发送消息并获取结果
     */
    @Test
    public void testSendMessageGetResult() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.sendMessageGetResult(topic, key, message);
    }

    /**
     * sendMessageAsync
     * <p>
     * 异步 回调
     */
    @Test
    public void sendMessageAsync() throws IOException {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.sendMessageAsync(topic, key, message);
        System.in.read();
    }

    /**
     * testMessageBuilder
     * <p>
     * 构建消息体
     */
    @Test
    public void testMessageBuilderMessage() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.testMessageBuilderMessage(topic, key, message);
    }

    /**
     * testMessageBuilderProducerRecord
     * <p>
     * 构建消息体
     */
    @Test
    public void testMessageBuilderProducerRecord() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.testMessageBuilderProducerRecord(topic, key, message);
    }

    /**
     * testSendMessageInTransaction
     * <p>
     * 测试事务
     */
    @Test
    public void testSendMessageInTransaction() {
        String topic = "kafka-single-topic";
        String key = "key";
        String message = "this is a message";
        kafkaProducerService.sendMessageInTransaction(topic, key, message);
    }


    /**
     * testConsumerBatch
     * <p>
     * 测试批量消费 测试前需要设置开启批量监听为true
     * <p>
     * 配置类型为批量
     * spring.kafka.listener.type:batch
     * <p>
     * 配置监听批量
     * factory.setBatchListener(true);
     * <p>
     * 配置批量最大数量
     * spring.kafka.consumer.max-poll-records: 10
     */
    @Test
    public void testConsumerBatch() throws IOException {
        // 写入多条数据到批量topic:hello-batch
        String topic = "kafka-batch-topic";
        for (int i = 0; i < 20; i++) {
            kafkaProducerService.sendMessageAsync(topic, null, "batch-message-" + i);
        }
        System.in.read();
    }


    /**
     * testConsumerInterceptor
     * <p>
     * 测试消费者拦截器
     * 消息被拦截器过滤了，没有消费，因为消息内容包含filter，这是前面自定义消费者拦截器的逻辑
     */
    @Test
    public void testConsumerInterceptor() {
        String topic = "kafka-interceptor-topic";
        kafkaProducerService.sendMessageSync(topic, null, "this is a normal-message");
        kafkaProducerService.sendMessageSync(topic, null, "this is a filtered message");
        kafkaProducerService.sendMessageSync(topic, null, "this is a filter message");
    }

    /**
     * testUserRegister
     * <p>
     * 测试模拟用户注册
     */
    @Test
    public void testUserRegister() {
        String topic = "register";
        User user = new User();
        user.setId("0001");
        user.setName("张三");
        user.setAge(20);
        kafkaProducerService.sendMessageSync(topic, JSON.toJSONString(user.getId()), JSON.toJSONString(user));
    }
}
