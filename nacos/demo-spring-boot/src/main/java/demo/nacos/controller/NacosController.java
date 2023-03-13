package demo.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NacosController
 *
 * @author Wenzhou
 * @date 2023/3/13 21:56
 */
@RestController
public class NacosController {
    /**
     * name
     */
    @NacosValue(value = "${name}", autoRefreshed = true)
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
