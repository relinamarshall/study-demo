package demo.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * NacosSpringBootApplication
 * {@link @NacosPropertySource}指定Nacos中配置文件,开启动态刷新
 * <p>
 * 如果在application.yml中配置,则还需增加开启配置选项
 * <p>
 * nacos.config.data-id: test
 * <p>
 * nacos.config.auto-refresh: true
 * <p>
 * nacos.config.bootstrap.enable: true
 *
 * @author Wenzhou
 * @since 2023/3/13 21:53
 */
@SpringBootApplication
//@NacosPropertySource(dataId = "test", autoRefreshed = true)
public class NacosSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosSpringBootApplication.class, args);
    }
}
