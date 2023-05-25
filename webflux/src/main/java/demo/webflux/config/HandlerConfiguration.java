package demo.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.webflux.handler.ImageCodeHandler;

/**
 * HandlerConfiguration
 *
 * @author Wenzhou
 * @since 2023/5/25 10:28
 */
@Configuration(proxyBeanMethods = false)
public class HandlerConfiguration {
    @Bean
    public ImageCodeHandler imageCodeHandler() {
        return new ImageCodeHandler();
    }
}
