package demo.zookeeper.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * ZooLock
 * <p>
 * 基于Zookeeper的分布式锁注解
 * 在需要加锁的方法上打上该注解后，AOP会帮助你统一管理这个方法的锁
 *
 * @author Wenzhou
 * @since 2023/3/28 17:11
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZooLock {
    /**
     * 分布式锁的键
     */
    String key();

    /**
     * 锁释放时间，默认五秒
     */
    long timeout() default 5 * 1000;

    /**
     * 时间格式，默认：毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
