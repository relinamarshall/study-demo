package demo.log.aop.aspectj;

import com.google.common.collect.Maps;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import demo.log.aop.util.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wenzhou
 * @since 2023/3/19 22:16
 */
@Slf4j
@Aspect
@Component
public class AopLog {
    /**
     * log
     * 切入点
     */
    @Pointcut("execution(public * demo.log.aop.controller.*Controller.*(..))")
    public void log() {
    }

    /**
     * aroundLog
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        final Log l = Log.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(IpUtil.getIp(request))
                .url(request.getRequestURL().toString())
                .classMethod(String.format("%s.%s",
                        point.getSignature().getDeclaringTypeName(),
                        point.getSignature().getName()))
                .httpMethod(request.getMethod())
                .requestParams(getNameAndValue(point))
                .result(result)
                .timeCost(System.currentTimeMillis() - startTime)
                .userAgent(header)
                .browser(userAgent.getBrowser().toString())
                .os(userAgent.getOperatingSystem().toString()).build();

        log.info("Request Log Info : {}", JSONUtil.toJsonStr(l));

        return result;
    }

    /**
     * getNameAndValue
     * 获取方法参数名和参数值
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Map<String, Object>
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    /**
     * Log
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Log {
        /**
         * 线程id
         */
        private String threadId;
        /**
         * 线程名称
         */
        // 线程名称
        private String threadName;
        /**
         * 线程名称
         */
        private String ip;
        /**
         * 线程名称
         */
        private String url;
        /**
         * http方法 GET POST PUT DELETE PATCH
         */
        private String httpMethod;
        /**
         * 类方法
         */
        private String classMethod;
        /**
         * 请求参数
         */
        private Object requestParams;
        /**
         * 返回参数
         */
        private Object result;
        /**
         * 接口耗时
         */
        private Long timeCost;
        /**
         * 操作系统
         */
        private String os;
        /**
         * 浏览器
         */
        private String browser;
        /**
         * user-agent
         */
        private String userAgent;
    }
}
