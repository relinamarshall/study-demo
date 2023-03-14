package demo.nacos.javasdk;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.io.IOException;
import java.util.List;

/**
 * Test01
 * 服务发现
 *
 * @author Wenzhou
 * @date 2023/3/14 21:39
 */
public class Test01 {
    public static void main(String[] args) throws NacosException, IOException {
        NamingService naming = NamingFactory.createNamingService("localhost:8848");
        List<Instance> instances = naming.selectInstances("user", true);
        for (Instance instance : instances) {
            System.out.println(instance);
        }

        // 参考值 权重
        System.out.println(naming.selectOneHealthyInstance("user"));
        System.out.println(naming.selectOneHealthyInstance("user"));
        System.out.println(naming.selectOneHealthyInstance("user"));
        System.out.println(naming.selectOneHealthyInstance("user"));
        System.out.println(naming.selectOneHealthyInstance("user"));

        naming.subscribe("user", event -> {
            if (event instanceof NamingEvent) {
                System.out.println(((NamingEvent) event).getServiceName());
                System.out.println(((NamingEvent) event).getInstances());
            }
        });

        System.in.read();
    }
}
