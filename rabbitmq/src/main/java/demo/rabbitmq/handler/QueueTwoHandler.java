package demo.rabbitmq.handler;

import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import cn.hutool.json.JSONUtil;
import demo.rabbitmq.constants.RabbitMQConstant;
import demo.rabbitmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;

/**
 * QueueTwoHandler
 * <p>
 * 队列2 处理器
 *
 * @author Wenzhou
 * @since 2023/3/24 10:48
 */
@Slf4j
@RabbitListener(queues = RabbitMQConstant.QUEUE_TWO)
@Component
public class QueueTwoHandler {

    /**
     * directHandlerManualAck
     *
     * @param messageStruct MessageStruct
     * @param message       Message
     * @param channel       Channel
     */
    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("队列2，手动ACK，接收消息：{}", JSONUtil.toJsonStr(messageStruct));
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
