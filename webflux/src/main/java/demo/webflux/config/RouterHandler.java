package demo.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import demo.webflux.handler.ImageCodeHandler;
import lombok.RequiredArgsConstructor;

/**
 * RouterHandler
 * <p>
 * 路由配置信息
 *
 * @author Wenzhou
 * @since 2023/5/25 10:04
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RouterHandler {

    private final ImageCodeHandler imageCodeHandler;

    @Bean
    public RouterFunction<ServerResponse> helloRouterFunction() {
        return RouterFunctions.route().GET("/hello/{name}", request -> {
            String name = request.pathVariable("name");
            return ServerResponse.ok().bodyValue(name);
        }).build();
    }

    @Bean
    public RouterFunction<ServerResponse> codeRouterFunction() {
        return RouterFunctions.route(RequestPredicates.path("/code/{id}")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), imageCodeHandler);
    }
}
