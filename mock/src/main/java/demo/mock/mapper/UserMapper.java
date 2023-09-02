package demo.mock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import demo.mock.entity.User;

/**
 * UserMapper
 *
 * @author Wenzhou
 * @since 2023/3/21 16:20
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * testBetweenDate
     *
     * @param begin String
     * @param end   String
     * @return List<User>
     */
    List<User> testBetweenDate(@Param("begin") String begin, @Param("end") String end);
}
