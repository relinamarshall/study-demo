package demo.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SecurityController
 *
 * @author Wenzhou
 * @since 2023/3/16 22:20
 */
@Controller
public class SecurityController {

    /**
     * index
     *
     * @return String
     */
    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * toLogin
     *
     * @return String
     */

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "views/login";
    }

    /**
     * level1
     *
     * @param id int
     * @return String
     */
    @PreAuthorize("hasAnyRole('vip3','vip2','vip1')")
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
    @PreAuthorize("hasAnyRole('vip3','vip2')")
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
    @PreAuthorize("hasRole('vip3')")
    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable("id") int id) {
        return "views/level3/" + id;
    }
}
