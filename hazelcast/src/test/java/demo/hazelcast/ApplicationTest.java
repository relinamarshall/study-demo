package demo.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ConcurrentMap;

import demo.hazelcast.config.HazelcastConfig;

/**
 * ApplicationTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {
    /**
     * instance
     */
    @Autowired
    @Qualifier(value = "HazelcastClient")
    private HazelcastInstance instance;
    /**
     * map
     */
    private ConcurrentMap<String, String> map;
    /**
     * topic
     */
    private ITopic<Object> topic;

    @Before
    public void before() {
        map = instance.getMap("test");
        topic = instance.getTopic(HazelcastConfig.HAZELCAST_TOPIC);
    }

    @Test
    public void testMap() {
        map.put("1", "a");
        map.put("1", "b");
        System.out.println(map.get("1"));
    }

    @Test
    public void testTopic() {
        topic.publish("test string");
        System.out.println(topic.getName());
        System.out.println(topic.getServiceName());
    }
}
