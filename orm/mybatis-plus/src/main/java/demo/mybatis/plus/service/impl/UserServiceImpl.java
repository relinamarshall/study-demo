package demo.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import demo.mybatis.plus.entity.User;
import demo.mybatis.plus.mapper.UserMapper;
import demo.mybatis.plus.service.UserService;

/**
 * UserServiceImpl
 *
 * @author Wenzhou
 * @since 2023/3/21 16:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> testBetweenDate(String begin, String end) {
        return baseMapper.testBetweenDate(begin, end);
    }

    /**
     * testIn
     * <p>
     * IN 的“第一个条件为true时才使用第三个参数执行sql处理”是in方法内部的逻辑，而不是调用方的逻辑。
     * <p>
     * 调用方所做的事情是把参数值传给in方法 当vo.getOprationType()#split时，由于vo.getOprationType()是null，所以导致了空指针。
     *
     * @param userNames String
     * @return List<User>
     */
    @Override
    public List<User> testIn(String userNames) {
        return list(new LambdaQueryWrapper<User>().in(StringUtils.isNotBlank(userNames), User::getName,
                userNames == null ? Collections.emptyList() : Arrays.asList(userNames.split(","))));
    }
}
