package demo.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * NacosSpringCloudConsumeApplication
 * 测试服务发现
 *
 * @author Wenzhou
 * @date 2023/3/14 21:56
 */
@SpringBootApplication
public class NacosSpringCloudConsumeApplication {
    /**
     * getRestTemplate
     *
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        return new NacosRule();
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosSpringCloudConsumeApplication.class, args);
    }
}
