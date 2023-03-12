package demo.apollo.controller;

import com.google.common.collect.Maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import demo.apollo.config.ConfigProperties;
import lombok.RequiredArgsConstructor;

/**
 * ConfigController
 *
 * @author Wenzhou
 * @date 2023/3/12 18:57
 */
@RestController
@RequiredArgsConstructor
public class ConfigController {
    /**
     * configProperties
     */
    private final ConfigProperties configProperties;

    /**
     * getName
     *
     * @return String
     */
    @GetMapping("/name")
    public String getName() {
        return configProperties.getName();
    }

    /**
     * getMysqlConfig
     *
     * @return Map<String, String>
     */
    @GetMapping("/mysql")
    public Map<String, String> getMysqlConfig() {
        Map<String, String> res = Maps.newHashMap();
        res.put("host", configProperties.getMysqlHost());
        res.put("port", configProperties.getMysqlPort());
        res.put("username", configProperties.getMysqlUsername());
        res.put("password", configProperties.getMysqlPassword());
        return res;
    }
}
