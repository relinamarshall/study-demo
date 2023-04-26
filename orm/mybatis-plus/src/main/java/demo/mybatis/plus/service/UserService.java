package demo.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import demo.mybatis.plus.entity.User;

/**
 * UserService
 *
 * @author Wenzhou
 * @since 2023/3/21 16:21
 */
public interface UserService extends IService<User> {
    /**
     * testBetweenDate
     *
     * @param begin String
     * @param end   String
     * @return List<User>
     */
    List<User> testBetweenDate(String begin, String end);
}
