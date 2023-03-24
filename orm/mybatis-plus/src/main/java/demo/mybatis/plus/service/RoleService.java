package demo.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

import demo.mybatis.plus.entity.Role;

/**
 * RoleService
 *
 * @author Wenzhou
 * @since 2023/3/24 16:01
 */
public interface RoleService extends IService<Role> {
    /**
     * getUserForMap
     *
     * @return Map<String, String>
     */
    Map<String, String> getUserForMap();
}
