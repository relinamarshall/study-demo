package demo.mock.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Repository;

import demo.mock.entity.User;
import demo.mock.mapper.UserMapper;
import demo.mock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserRepositoryImpl
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl extends ServiceImpl<UserMapper, User> implements UserRepository {

}