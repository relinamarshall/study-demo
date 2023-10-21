package demo.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import demo.hazelcast.entity.Customer;
import lombok.SneakyThrows;

/**
 * ApplicationTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/14
 */
public class BaseTest {
    /**
     * client
     */
    private HazelcastInstance client;
    /**
     * config
     */
    private final ClientConfig config = new ClientConfig();

    @Before
    public void before() {
        //config.getNetworkConfig().addAddress("192.168.139.233:5701");
        System.out.println(config);
        client = HazelcastClient.newHazelcastClient(config);
    }

    @After
    public void after() {
        client.shutdown();
    }

    @Test
    public void testMap() {
        IMap<String, Customer> map = client.getMap("customers");
        map.put("1", new Customer("Joe", "Smith"));
        map.put("2", new Customer("Ali", "Selam"));
        map.put("3", new Customer("Avi", "Noyan"));

        for (Map.Entry<String, Customer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Test
    @SneakyThrows
    public void testQueue() {
        BlockingQueue<String> queue = client.getQueue("queue");
        for (int i = 0; i < 30; i++) {
            queue.put("Hello!" + i);
        }

        //String remove = queue.remove();
        //System.out.println(remove);
    }
}
