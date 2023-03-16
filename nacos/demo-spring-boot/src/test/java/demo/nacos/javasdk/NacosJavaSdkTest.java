package demo.nacos.javasdk;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author Wenzhou
 * @since 2023/3/13 20:56
 */
public class NacosJavaSdkTest {
    @Test
    public void test() {
        try {
            String serverAddr = "localhost:8848";
            String dataId = "test";
            String group = "DEFAULT_GROUP";

            Properties properties = new Properties();
            properties.put("serverAddr", serverAddr);

            ConfigService configService = NacosFactory.createConfigService(properties);
            String context = configService.getConfig(dataId, group, 5000);

            System.out.println(context);

            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String s) {
                    System.out.println("receiveConfigInfo:" + s);
                }
            });

            System.in.read();
        } catch (NacosException | IOException e) {
            e.printStackTrace();
        }
    }
}
