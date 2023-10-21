package demo.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

/**
 * JavaTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/15
 */
public class JavaTest {
    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        HazelcastInstance server = Hazelcast.newHazelcastInstance();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);

        IMap<Object, Object> map = server.getMap("articlesEvictionPolicy");
        System.out.println("map: " + map.size());
        System.out.println("server: " + server.getCluster());
        System.out.println("client:" + client.getCluster());

        client.shutdown();
        server.shutdown();
    }
}
