package demo.shiro.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import demo.shiro.mapper.PermissionMapper;
import demo.shiro.pojo.Permission;

/**
 * ShiroConfig
 *
 * @author Wenzhou
 * @since 2023/3/16 21:05
 */
@Configuration
public class ShiroConfig {
    /**
     * permissionMapper
     */
    private final PermissionMapper permissionMapper;

    /**
     * ShiroConfig
     *
     * @param permissionMapper PermissionMapper
     */
    public ShiroConfig(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 开启Shiro的注解
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启 shiro aop注解支持
     *
     * @param manager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") DefaultWebSecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    /**
     * getShiroFilterFactoryBean
     *
     * @param manager DefaultWebSecurityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(manager);
        /* DefaultFilter
         * anon   无需认证就可以访问
         * auths  必须认证才能访问
         * user   必须拥有 记住我功能才能使用
         * perms  拥有对某个资源的权限才能访问
         * roles  拥有某个角色权限才能访问
         * */
        Map<String, String> filterMap = new HashMap<>();
        List<Permission> permissions = permissionMapper.queryAll();
        permissions.forEach(permission -> {
            // 注解开发使用 @RequiresPermissions()
            filterMap.put(permission.getUrl() + "/*", "perms[" + permission.getCode() + "]");
        });
        bean.setFilterChainDefinitionMap(filterMap);
        // 设置登录请求
        bean.setLoginUrl("/toLogin");
        // 未授权页面
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    /**
     * 2：DefaultWebSecurityManager
     *
     * @param userRealm
     * @return DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 1：创建Realm对象
     *
     * @return UserRealm
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * ShiroDialect
     *
     * @return ShiroDialect
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
