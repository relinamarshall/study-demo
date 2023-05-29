package demo.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * MongoDBApplication
 *
 * @author Wenzhou
 * @since 2023/5/29 16:32
 */
@SpringBootApplication
public class MongoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoDBApplication.class, args);
    }

    @Bean
    public Snowflake snowflake() {
        return IdUtil.getSnowflake(1, 1);
    }
}
