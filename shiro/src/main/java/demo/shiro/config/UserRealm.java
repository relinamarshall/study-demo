package demo.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import demo.shiro.mapper.UserMapper;
import demo.shiro.pojo.User;
import lombok.extern.slf4j.Slf4j;

/**
 * UserRealm
 *
 * @author Wenzhou
 * @since 2023/3/16 21:06
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    /**
     * userMapper
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        log.info("执行了 ==> 授权 doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        // 拿到当前登录的对象
        User user = (User) subject.getPrincipal();
        List<String> permission = userMapper.findPermissionByUserId(user.getId());
        permission.forEach(info::addStringPermission);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        log.info("执行了 ==> 认证 doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) at;
        User user = userMapper.findUserByName(token.getUsername());
        if (user == null) {
            // 报没有用户异常
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
