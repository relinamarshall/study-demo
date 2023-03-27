package demo.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

import demo.mybatis.plus.entity.Role;

/**
 * RoleMapper
 *
 * @author Wenzhou
 * @since 2023/3/21 16:21
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * getUserForMap2
     *
     * @param resultHandler ResultHandler
     */
    void getUserForMap2(@Param("id") String id, ResultHandler resultHandler);
}
