package demo.shiro.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import demo.shiro.pojo.User;

/**
 * UserMapper
 *
 * @author Wenzhou
 * @since 2023/3/16 20:54
 */
@Mapper
public interface UserMapper {
    /**
     * 查询用户
     *
     * @param name
     * @return UserPo
     */
    @Select("select id,username,password,fullname,mobile from  shiro.t_user where username=#{name}")
    User findUserByName(@Param("name") String name);

    /**
     * 根据用户角色查询权限
     *
     * @param id
     * @return
     */
    @Select("SELECT code FROM shiro.t_permission WHERE id IN (" +
            "SELECT permission_id FROM shiro.t_role_permission WHERE role_id IN ( " +
            "SELECT role_id FROM shiro.t_user_role WHERE user_id = #{id} ));")
    List<String> findPermissionByUserId(@Param("id") Integer id);
}
