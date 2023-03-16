package demo.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * LevelController
 *
 * @author Wenzhou
 * @since 2023/3/16 20:59
 */
@Controller
public class LevelController {

    /**
     * level1
     *
     * @param id int
     * @return String
     */
    //@RequiresPermissions("vip1")
    @RequestMapping("/level1/{id}")
    public String level1(@PathVariable("id") int id) {
        return "views/level1/" + id;
    }

    /**
     * level2
     *
     * @param id int
     * @return String
     */
    //@RequiresPermissions("vip2")
    @RequestMapping("/level2/{id}")
    public String level2(@PathVariable("id") int id) {
        return "views/level2/" + id;
    }

    /**
     * level3
     *
     * @param id int
     * @return String
     */
    //@RequiresPermissions("vip3")
    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable("id") int id) {
        return "views/level3/" + id;
    }
}
