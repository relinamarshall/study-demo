package demo.nacos.spring.config;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import org.springframework.context.annotation.ComponentScan;

/**
 * @author Wenzhou
 * @since 2023/3/13 21:45
 */
@ComponentScan("demo.nacos.spring")
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
@NacosPropertySource(dataId = "test", autoRefreshed = true)
public class MyConfig {
}
