package demo.nacos.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import demo.nacos.spring.config.MyConfig;
import demo.nacos.spring.config.NacosProps;


/**
 * NacosSpringTest
 *
 * @author Wenzhou
 * @date 2023/3/13 21:42
 */
public class NacosSpringTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        NacosProps nacosProps = applicationContext.getBean(NacosProps.class);
        System.out.println(nacosProps.getName());
    }
}
