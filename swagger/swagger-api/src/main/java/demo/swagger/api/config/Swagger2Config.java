package demo.swagger.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2Config
 *
 * @author Wenzhou
 * @since 2023/3/21 11:12
 */
@Configuration
@EnableSwagger2
//@EnableSwaggerBootstrapUI
@EnableOpenApi
public class Swagger2Config {
    /**
     * createRestApi
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("demo.swagger.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * apiInfo
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo-Swagger2")
                .description("这是一个简单的 Swagger API 演示")
                .contact(new Contact("RelinaMarshall", "https://www.baidu.com", "726888283@qq.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
