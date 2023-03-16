package demo.redis.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import demo.redis.entity.User;
import demo.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;

/**
 * RedisController
 *
 * @author Wenzhou
 * @since 2023/3/16 19:52
 */
@RestController
@RequiredArgsConstructor
public class RedisController {
    public static final String INDEX_NAME = "user:";
    /**
     * redisUtil
     */
    public final RedisUtil redisUtil;

    /**
     * getUserById
     *
     * @param id Long
     * @return {@link demo.redis.entity.User}
     */
    @GetMapping("/get/{id}")
    @Cacheable
    public User getUserById(@PathVariable("id") Long id) {
        User user = null;
        if (redisUtil.hasKey(INDEX_NAME + id)) {
            user = (User) redisUtil.get(INDEX_NAME + id);
        }
        return user;
    }

    /**
     * addUser
     *
     * @param id   Long
     * @param name String
     * @return {@link demo.redis.entity.User}
     */
    @GetMapping("/add/{id}/{name}")
    public User addUser(@PathVariable("id") Long id,
                        @PathVariable("name") String name) {
        User user = new User(id, name);
        if (!redisUtil.hasKey(INDEX_NAME + id)) {
            redisUtil.set(INDEX_NAME + id, user);
        } else {
            user = (User) redisUtil.get(INDEX_NAME + id);
        }

        return user;
    }

    /**
     * deleteUserById
     *
     * @param id Long
     * @return {@link demo.redis.entity.User}
     */
    @GetMapping("/delete/{id}")
    public User deleteUserById(@PathVariable("id") Long id) {
        User user = null;
        if (redisUtil.hasKey(INDEX_NAME + id)) {
            user = ((User) redisUtil.get(INDEX_NAME + id));
            redisUtil.del(INDEX_NAME + id);
        }
        return user;
    }

    /**
     * updateUser
     *
     * @param id   Long
     * @param name String
     * @return {@link demo.redis.entity.User}
     */
    @GetMapping("/update/{id}/{name}")
    public User updateUser(@PathVariable("id") Long id,
                           @PathVariable("name") String name) {
        User user = new User(id, name);
        if (redisUtil.hasKey(INDEX_NAME + id)) {
            redisUtil.set(INDEX_NAME + id, user);
        }
        return user;
    }
}
