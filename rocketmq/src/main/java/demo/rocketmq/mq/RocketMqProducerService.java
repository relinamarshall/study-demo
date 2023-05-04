package demo.rocketmq.mq;

import com.alibaba.fastjson.JSON;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import demo.rocketmq.constants.RocketMQ;
import demo.rocketmq.pojo.User;
import lombok.extern.slf4j.Slf4j;

/**
 * RocketMqProducerService
 *
 * @author Wenzhou
 * @since 2023/5/4 15:23
 */
@Slf4j
@Service
public class RocketMqProducerService {
    /**
     * messageTimeOut
     */
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    /**
     * rocketMQTemplate
     */
    private final RocketMQTemplate rocketMQTemplate;

    /**
     * RocketMqProducerService
     *
     * @param rocketMQTemplate RocketMQTemplate
     */
    public RocketMqProducerService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 普通发送
     * <p>
     * （这里的参数对象User可以随意定义，可以发送个对象，也可以是字符串等）
     */
    public void send(User user) {
        rocketMQTemplate.convertAndSend(RocketMQ.TOPIC + ":tag1", user);
        //rocketMQTemplate.send(topic + ":tag1", MessageBuilder.withPayload(user).build()); // 等价于上面一行
    }

    /**
     * 发送同步消息
     * <p>
     * （阻塞当前线程，等待broker响应发送结果，这样不太容易丢失消息）
     * <p>
     * （msgBody也可以是对象，sendResult为返回的发送结果）
     */
    public SendResult sendMsg(String msgBody) {
        SendResult sendResult = rocketMQTemplate.syncSend(RocketMQ.TOPIC, MessageBuilder.withPayload(msgBody).build());
        log.info("【sendMsg】sendResult={}", JSON.toJSONString(sendResult));
        return sendResult;
    }

    /**
     * 发送异步消息
     * <p>
     * （通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
     * <p>
     * （适合对响应时间敏感的业务场景）
     */
    public void sendAsyncMsg(String msgBody) {
        rocketMQTemplate.asyncSend(RocketMQ.TOPIC, MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("【Success】处理消息发送成功逻辑");
                // 处理消息发送成功逻辑
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("【Exception】处理消息发送异常逻辑");
                // 处理消息发送异常逻辑
            }
        });
    }

    /**
     * 发送延时消息
     * <p>
     * （上面的发送同步消息，delayLevel的值就为0，因为不延时）
     * <p>
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void sendDelayMsg(String msgBody, int delayLevel) {
        rocketMQTemplate.syncSend(RocketMQ.TOPIC, MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
    }

    /**
     * 发送单向消息
     * <p>
     * （只负责发送消息，不等待应答，不关心发送结果，如日志）
     */
    public void sendOneWayMsg(String msgBody) {
        rocketMQTemplate.sendOneWay(RocketMQ.TOPIC, MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送带tag的消息，直接在topic后面加上":tag"
     */
    public SendResult sendTagMsg(String msgBody) {
        return rocketMQTemplate.syncSend(RocketMQ.TOPIC + ":tag2", MessageBuilder.withPayload(msgBody).build());
    }
}
