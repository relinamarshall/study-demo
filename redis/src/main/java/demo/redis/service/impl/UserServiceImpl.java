package demo.redis.service.impl;

import com.google.common.collect.Maps;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

import demo.redis.entity.User;
import demo.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * UserServiceImpl
 *
 * @author Wenzhou
 * @since 2023/3/16 16:27
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    /**
     * 模拟数据库
     */
    private static final Map<Long, User> DATABASES = Maps.newConcurrentMap();

    /**
     * 初始化数据
     */
    static {
        DATABASES.put(1L, new User(1L, "张三"));
        DATABASES.put(2L, new User(2L, "李四"));
        DATABASES.put(3L, new User(3L, "王五"));
    }

    /**
     * 保存或修改用户
     *
     * @param user 用户对象
     * @return
     */
    @Override
    @CachePut(value = "user", key = "#user.id")
    public User saveOrUpdate(User user) {
        DATABASES.put(user.getId(), user);
        log.info("保存用户【user】= {}", user);
        return user;
    }

    /**
     * 获取用户
     *
     * @param id key值
     * @return 返回结果
     */
    @Override
    @Cacheable(value = "user", key = "#id")
    public User get(Long id) {
        // 我们假设从数据库读取
        log.info("查询用户【id】= {}", id);
        return DATABASES.get(id);
    }

    /**
     * 删除
     *
     * @param id key值
     */
    @Override
    @CacheEvict(value = "user", key = "#id")
    public void delete(Long id) {
        DATABASES.remove(id);
        log.info("删除用户【id】= {}", id);
    }
}
