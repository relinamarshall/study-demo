package demo.rocketmq.controller;

import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.rocketmq.mq.RocketMqProducerService;
import demo.rocketmq.pojo.User;
import lombok.RequiredArgsConstructor;

/**
 * RocketMQController
 *
 * @author Wenzhou
 * @since 2023/5/4 16:52
 */
@RestController
@RequestMapping("/mq")
@RequiredArgsConstructor
public class RocketMQController {
    /**
     * rocketMqProducerService
     */
    private final RocketMqProducerService producerService;

    /**
     * 普通发送
     */
    @GetMapping("/send")
    public void send() {
        User user = new User("你好", "浙江");
        producerService.send(user);
    }

    /**
     * 发送同步消息
     *
     * @return SendResult
     */
    @GetMapping("/sendSync")
    public SendResult sendSync() {
        return producerService.sendMsg("同步消息");
    }

    /**
     * 发送异步消息
     */
    @GetMapping("/sendAsync")
    public void sendAsync() {
        producerService.sendAsyncMsg("异步消息");
    }

    /**
     * 发送延时消息
     */
    @GetMapping("/sendDelay/{level}")
    public void sendDelay(@PathVariable("level") int level) {
        producerService.sendDelayMsg("延迟消息", level);
    }

    /**
     * 发送单向消息
     */
    @GetMapping("/sendOneWay")
    public void sendOneWay() {
        producerService.sendOneWayMsg("单向消息");
    }

    /**
     * 发送带tag的消息
     *
     * @return SendResult
     */
    @GetMapping("/sendTag")
    public SendResult sendTag() {
        return producerService.sendTagMsg("带有tag的字符消息");
    }

}
