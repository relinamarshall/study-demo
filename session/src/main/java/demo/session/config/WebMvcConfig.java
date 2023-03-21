package demo.session.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import demo.session.interceptor.SessionInterceptor;
import lombok.RequiredArgsConstructor;

/**
 * WebMvcConfig
 *
 * @author Wenzhou
 * @since 2023/3/21 15:08
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * sessionInterceptor
     */
    private final SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration sessionInterceptorRegistry = registry.addInterceptor(sessionInterceptor);
        // 排除不需要拦截的路径
        sessionInterceptorRegistry.excludePathPatterns("/page/login");
        sessionInterceptorRegistry.excludePathPatterns("/page/doLogin");
        sessionInterceptorRegistry.excludePathPatterns("/error");

        // 需要拦截的路径
        sessionInterceptorRegistry.addPathPatterns("/**");
    }
}
