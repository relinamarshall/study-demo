package demo.nacos.javasdk;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.io.IOException;
import java.util.HashMap;

/**
 * TestNacosService
 * 服务注册
 *
 * @author Wenzhou
 * @date 2023/3/14 21:29
 */
public class TestNacosService {
    public static void main(String[] args) throws NacosException, IOException {
        NamingService naming = NamingFactory.createNamingService("localhost:8848");
        naming.registerInstance("user", "11.11.11.11", 8888, "beijing");

        NamingService naming2 = NamingFactory.createNamingService("localhost:8848");
        naming2.registerInstance("user", "11.11.11.12", 8888, "beijing");

        NamingService naming3 = NamingFactory.createNamingService("localhost:8848");
        naming3.registerInstance("user", "11.11.11.13", 8888, "shanghai");

        NamingService naming4 = NamingFactory.createNamingService("localhost:8848");
        Instance instance = new Instance();
        instance.setIp("11.11.11.14");
        instance.setPort(8888);
        instance.setHealthy(false);
        instance.setWeight(2);
        HashMap<String, String> instanceMeta = new HashMap<>();
        instanceMeta.put("site", "et2");
        instance.setMetadata(instanceMeta);
        naming4.registerInstance("user", instance);
        System.in.read();
    }
}
