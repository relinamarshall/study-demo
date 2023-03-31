package demo.admin.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * IndexController
 *
 * @author Wenzhou
 * @since 2023/3/31 11:29
 */
@Slf4j
@RestController
public class IndexController {
    @GetMapping(value = {"", "/"})
    public String index() {
        return "This is a Spring Boot Admin Client.";
    }

    @GetMapping("log")
    public void log() {
        log.info("This is a Spring Boot Admin Client.");
        log.debug("This is a Spring Boot Admin Client.");
        log.error("This is a Spring Boot Admin Client.");
    }
}
