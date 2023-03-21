package demo.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

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
}
