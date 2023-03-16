package demo.security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import demo.security.mapper.UserMapper;
import demo.security.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * MysqlUserDetailServiceImpl
 *
 * @author Wenzhou
 * @since 2023/3/16 22:23
 */
@Slf4j
@Component
public class MysqlUserDetailServiceImpl implements UserDetailsService {
    /**
     * userMapper
     */
    private final UserMapper userMapper;

    /**
     * MysqlUserDetailService
     *
     * @param userMapper UserMapper
     */
    public MysqlUserDetailServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        demo.security.pojo.User user = userMapper.findUserByName(name);
        AssertUtil.assertNotNull(user, () -> {
            log.error("用户不存在");
            return new UsernameNotFoundException("用户不存在");
        });

        List<String> permissionList = userMapper.findPermissionByUserId(user.getId());
        String[] permissions = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            /*
              //源码中
              public UserBuilder roles(String... roles) {
                   List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
                   for (String role : roles) {
                   添加的role必须以ROLE_开头
                   	Assert.isTrue(!role.startsWith("ROLE_"),
                   			() -> role + " cannot start with ROLE_ (it is automatically added)");
                   	authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                                     }
                   return authorities(authorities);
              }
             */
            permissions[i] = "ROLE_" + permissionList.get(i);
        }
        String auth = Arrays.toString(permissions);
        String authSubstring = auth.substring(1, auth.length() - 1);
        log.info("{}=>{}", user.getUsername(), authSubstring);
        // 构建权限 不用这个直接 填 authSubstring 也可以
        List<GrantedAuthority> authList = AuthorityUtils.commaSeparatedStringToAuthorityList(authSubstring);
        // 构建UserDetail对象 用户、密码、权限 都来自数据库
        return User.withUsername(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(authList).build();
    }
}
