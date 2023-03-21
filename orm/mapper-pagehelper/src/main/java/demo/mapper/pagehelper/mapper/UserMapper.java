package demo.mapper.pagehelper.mapper;

import org.springframework.stereotype.Component;

import demo.mapper.pagehelper.entity.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * UserMapper
 *
 * @author Wenzhou
 * @since 2023/3/21 16:50
 */
@Component
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}