package demo.nacos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * CommonConfig
 * 定义公共配置,配置中心所有配置
 * {@link @RefreshScope} 开启动态刷新
 *
 * @author Wenzhou
 * @since 2023/3/13 23:20
 */
@Component
@RefreshScope
public class CommonConfig {
    /**
     * name
     */
    @Value(value = "${name}")
    private String name;

    /**
     * getName
     *
     * @return
     */
    public String getName() {
        return name;
    }
}
