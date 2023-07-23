package demo.minio.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

/**
 * MinioConfiguration
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/7/23
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class MinioConfiguration {
    @Bean
    public MinioClient minioClient(OssProperties properties) {
        return MinioClient.builder().endpoint(properties.getMinio().getUrl())
                .credentials(properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey())
                .build();
    }
}
