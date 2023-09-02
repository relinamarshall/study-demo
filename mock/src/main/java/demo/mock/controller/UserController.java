package demo.mock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.mock.service.UserService;
import demo.mock.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserController
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    /**
     * userService
     */
    private final UserService userService;

    @GetMapping("{id}")
    public UserVo getUserName(@PathVariable("id") String id) {
        return userService.getUserName(id);
    }
}
