package demo.kafka.consumer.service;

import com.alibaba.fastjson.JSON;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import demo.kafka.consumer.entity.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wenzhou
 * @since 2023/3/28 10:25
 */
@Slf4j
@Configuration
public class UserConsumer {
    /**
     * consumer
     *
     * @param message String
     */
    @KafkaListener(topics = "register")
    public void consumer(String message) {
        log.info("consumer 接受到信息:{}", message);
        User user = JSON.parseObject(message, User.class);
        log.info("正在为{}办理注册业务,请稍等", user.getName());
        log.info("注册成功");
    }
}

