package demo.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DemoService02
 *
 * @author Wenzhou
 * @date 2023/3/12 18:17
 */
@SpringBootApplication
@EnableApolloConfig
public class DemoService {
    public static void main(String[] args) {
        SpringApplication.run(DemoService.class, args);
    }
}
