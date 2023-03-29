package demo.zookeeper.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LockKeyParam
 * <p>
 * 分布式锁动态key注解，配置之后key的值会动态获取参数内容
 *
 * @author Wenzhou
 * @since 2023/3/28 17:11
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface LockKeyParam {
    /**
     * 如果动态key在user对象中，那么就需要设置fields的值为user对象中的属性名可以为多个，基本类型则不需要设置该值
     * <p>例1：public void count(@LockKeyParam({"id"}) User user)
     * <p>例2：public void count(@LockKeyParam({"id","userName"}) User user)
     * <p>例3：public void count(@LockKeyParam String userId)
     */
    String[] fields() default {};
}
