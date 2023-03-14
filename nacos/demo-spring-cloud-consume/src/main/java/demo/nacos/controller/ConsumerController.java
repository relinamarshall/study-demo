package demo.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * ConsumerController
 *
 * @author Wenzhou
 * @date 2023/3/14 21:58
 */
@RestController
@RequiredArgsConstructor
public class ConsumerController {
    /**
     * restTemplate
     */
    private final RestTemplate restTemplate;

    /**
     * getTest
     * 测试服务发现
     *
     * @return String
     */
    @GetMapping("/test")
    public String getTest() {
        return restTemplate.getForObject("http://test/test", String.class);
    }


    /**
     * getName
     * 测试服务发现
     *
     * @return String
     */
    @GetMapping("/name")
    public String getName() {
        return restTemplate.getForObject("http://test/name", String.class);
    }
}
