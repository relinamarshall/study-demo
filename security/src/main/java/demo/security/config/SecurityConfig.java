package demo.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import demo.security.mapper.PermissionMapper;
import demo.security.pojo.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SecurityConfig
 *
 * @author Wenzhou
 * @since 2023/3/16 23:01
 */
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
// 开启Security注解
 @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * userDetailService
     */
    private final MysqlUserDetailServiceImpl userDetailService;
    /**
     * permissionMapper
     */
    private final PermissionMapper permissionMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<Permission> permissions = permissionMapper.queryAll();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.authorizeRequests().antMatchers("/").permitAll();
        for (Permission permission : permissions) {
            log.info("{},{}", permission.getUrl() + "/**", permission.getCode());
            registry.antMatchers(permission.getUrl() + "/**").hasRole(permission.getCode());
        }
        http.formLogin().loginPage("/toLogin")
                .usernameParameter("user")
                .passwordParameter("pwd")
                .loginProcessingUrl("/login");
        // 开启注销功能
        // 防止网站攻击 get post
        // 关闭csrf功能
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/");
        // 配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/error/403.html");

        // 开启记住我功能 cookie 默认保存两周
        http.rememberMe();
        // 自定义接受前端的参数 记住我 功能
        http.rememberMe().rememberMeParameter("remember");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
