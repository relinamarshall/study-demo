package demo.qiniuyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * IndexController
 *
 * @author Wenzhou
 * @since 2023/3/21 18:59
 */
@Controller
public class IndexController {
    /**
     * index
     *
     * @return String
     */
    @GetMapping({"", "/index"})
    public String index() {
        return "index";
    }
}
