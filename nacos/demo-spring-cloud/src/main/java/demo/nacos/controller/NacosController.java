package demo.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NacosController
 * {@link @RefreshScope}动态同步刷新配置中心
 *
 * @author Wenzhou
 * @date 2023/3/13 21:56
 */
@RestController
@RefreshScope
public class NacosController {
    /**
     * name
     */
    @Value(value = "${name}")
    private String name;

    /**
     * getName
     *
     * @return String
     */
    @GetMapping("name")
    public String getName() {
        return name;
    }
}
