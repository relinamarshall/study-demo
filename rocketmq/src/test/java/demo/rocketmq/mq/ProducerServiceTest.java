package demo.rocketmq.mq;

import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import demo.rocketmq.RocketMQApplicationTest;
import demo.rocketmq.pojo.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wenzhou
 * @since 2023/5/4 17:14
 */
@Slf4j
public class ProducerServiceTest extends RocketMQApplicationTest {
    /**
     * producerService
     */
    @Autowired
    private RocketMqProducerService producerService;

    /**
     * 普通消息
     */
    @Test
    public void testSend() {
        producerService.send(new User("你", "好"));
    }

    /**
     * 发送同步消息
     */
    @Test
    public void sendSync() {
        SendResult r = producerService.sendMsg("同步消息");
        log.info("{}", r);
    }

    /**
     * 发送异步消息
     */
    @Test
    public void sendAsync() {
        producerService.sendAsyncMsg("异步消息");
    }

    /**
     * 发送延时消息
     */
    @Test
    public void sendDelay() {
        producerService.sendDelayMsg("延迟消息", 1);
    }

    /**
     * 发送单向消息
     */
    @Test
    public void sendOneWay() {
        producerService.sendOneWayMsg("单向消息");
    }

    /**
     * 发送带tag的消息
     */
    @Test
    public void sendTag() {
        SendResult r = producerService.sendTagMsg("带有tag的字符消息");
        log.info("{}", r);
    }
}
