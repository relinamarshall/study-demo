package demo.hazelcast.listener;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * TopicListener
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/15
 */
@Slf4j
public class TopicListener implements MessageListener<String> {

    @Override
    public void onMessage(Message<String> message) {
        String msg = message.getMessageObject();
        log.info("订阅者，收到Topic消息：{}", msg);
    }
}
