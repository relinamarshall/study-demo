package demo.nacos.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * NacosProps
 *
 * @author Wenzhou
 * @since 2023/3/13 21:43
 */
@Data
@Component
public class NacosProps {
    /**
     * name
     */
    @Value("${name}")
    private String name;
}
