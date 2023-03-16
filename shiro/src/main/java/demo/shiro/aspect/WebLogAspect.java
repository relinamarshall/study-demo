package demo.shiro.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

/**
 * WebLogAspect
 *
 * @author Wenzhou
 * @since 2023/3/16 21:00
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    /**
     * startTime
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * webLog
     */
    @Pointcut("execution(public * demo.shiro.controller..*.*(..))")
    public void webLog() {
    }

    /**
     * doBefore
     *
     * @param joinPoint JoinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        // 接收到请求 记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        // 记录下请求内容
        log.info("\n" +
                        "Url : {}\n" +
                        "Http_Method : {}\n" +
                        "Ip : {}\n" +
                        "ClassMethod : {}\n" +
                        "Args : {}",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * doAfterReturning
     *
     * @param ret Object
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("SpendTime : {}ms; Response : {}", (System.currentTimeMillis() - startTime.get()), ret);
        // 用完之后清除 防止内存泄漏
        startTime.remove();
    }
}
