package demo.rabbitmq;

import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.date.DateUtil;
import demo.rabbitmq.constants.RabbitMQConstant;
import demo.rabbitmq.message.MessageStruct;

/**
 * RabbitMQTest
 *
 * @author Wenzhou
 * @since 2023/3/24 11:16
 */
public class RabbitMQTest extends RabbitMQApplicationTest {
    /**
     * rabbitTemplate
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试直接模式发送
     */
    @Test
    public void sendDirect() {
        rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_MODE_QUEUE_ONE,
                new MessageStruct("direct message"));
    }

    /**
     * 测试分列模式发送
     */
    @Test
    public void sendFanout() {
        rabbitTemplate.convertAndSend(RabbitMQConstant.FANOUT_MODE_QUEUE, "",
                new MessageStruct("fanout message"));
    }

    /**
     * 测试主题模式发送1
     */
    @Test
    public void sendTopic1() {
        rabbitTemplate.convertAndSend(RabbitMQConstant.TOPIC_MODE_QUEUE, "queue.aaa.bbb",
                new MessageStruct("topic message"));
    }

    /**
     * 测试主题模式发送2
     */
    @Test
    public void sendTopic2() {
        rabbitTemplate.convertAndSend(RabbitMQConstant.TOPIC_MODE_QUEUE, "ccc.queue",
                new MessageStruct("topic message"));
    }

    /**
     * 测试主题模式发送3
     */
    @Test
    public void sendTopic3() {
        rabbitTemplate.convertAndSend(RabbitMQConstant.TOPIC_MODE_QUEUE, "3.queue",
                new MessageStruct("topic message"));
    }

    /**
     * 测试延迟队列发送
     */
    @Test
    public void sendDelay() {
        rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_MODE_QUEUE, RabbitMQConstant.DELAY_QUEUE,
                new MessageStruct("delay message, delay 5s, " + DateUtil.date()), message -> {
                    message.getMessageProperties().setHeader("x-delay", 5000);
                    return message;
                });
        rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_MODE_QUEUE, RabbitMQConstant.DELAY_QUEUE,
                new MessageStruct("delay message,  delay 2s, " + DateUtil.date()), message -> {
                    message.getMessageProperties().setHeader("x-delay", 2000);
                    return message;
                });
        rabbitTemplate.convertAndSend(RabbitMQConstant.DELAY_MODE_QUEUE, RabbitMQConstant.DELAY_QUEUE,
                new MessageStruct("delay message,  delay 8s, " + DateUtil.date()), message -> {
                    message.getMessageProperties().setHeader("x-delay", 8000);
                    return message;
                });
    }
}
