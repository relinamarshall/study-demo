package demo.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.nacos.config.CommonConfig;
import lombok.RequiredArgsConstructor;

/**
 * NacosController
 * {@link @RefreshScope}动态同步刷新配置中心
 *
 * @author Wenzhou
 * @date 2023/3/13 21:56
 */
@RestController
@RefreshScope
@RequiredArgsConstructor
public class NacosController {
    /**
     * name
     */
    @Value(value = "${name}")
    private String name;

    /**
     * timeout
     */
    @Value("${timeout}")
    private Integer timeout;

    /**
     * commonConfig
     */
    private final CommonConfig commonConfig;

    /**
     * getName
     *
     * @return String
     */
    @GetMapping("/name")
    public String getName() {
        return commonConfig.getName() + ":" + timeout;
    }

    /**
     * port
     * 测试权重 查看Ip
     */
    @Value("${server.port}")
    private String port;

    /**
     * getTest
     * 测试服务注册
     *
     * @return String
     */
    @GetMapping("/test")
    public String getTest() {
        return "test" + port;
    }
}
