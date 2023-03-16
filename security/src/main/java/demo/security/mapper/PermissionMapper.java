package demo.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import demo.security.pojo.Permission;

/**
 * PermissionMapper
 *
 * @author Wenzhou
 * @since 2023/3/16 22:18
 */
@Mapper
public interface PermissionMapper {
    /**
     * 查询所有权限
     *
     * @return List<Permission>
     */
    @Select("select * from shiro.t_permission")
    List<Permission> queryAll();
}
