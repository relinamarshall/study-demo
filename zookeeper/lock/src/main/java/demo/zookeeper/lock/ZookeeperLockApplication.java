package demo.zookeeper.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ZookeeperLockApplication
 *
 * @author Wenzhou
 * @since 2023/3/28 17:11
 */
@SpringBootApplication
public class ZookeeperLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZookeeperLockApplication.class, args);
    }
}
