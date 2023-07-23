package demo.minio.config;

import lombok.Data;

/**
 * MinioConfig
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/7/23
 */
@Data
public class MinioProperties {
    /**
     * 外网访问服务地址
     */
    private String url;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 桶名称
     */
    private String bucketName;
}
