package demo.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * IndexController
 *
 * @author Wenzhou
 * @since 2023/3/16 20:55
 */
@Controller
public class IndexController {

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
     * login
     *
     * @param username String
     * @param pwd      String
     * @param model    Model
     * @return String String
     */
    @RequestMapping("/login")
    public String login(@RequestParam("user") String user, @RequestParam("pwd") String pwd, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user, pwd);
        try {
            // 执行登录的方法 没异常就说明ok了
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            // 用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "views/login";
        } catch (IncorrectCredentialsException e) {
            // 密码不存在
            model.addAttribute("msg", "密码错误");
            return "forward:toLogin";
        }
    }

    /**
     * logout
     *
     * @return String
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "forward:toLogin";
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
     * unauthorized
     *
     * @return String
     */
    @ResponseBody
    @RequestMapping("/noauth")
    public String unauthorized() {
        return "未经授权无法访问此页面";
    }
}
