package demo.mock;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * MockApplicationTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@RunWith(PowerMockRunner.class)
@MapperScan("demo.mock.mapper")
@ComponentScan(basePackages = {"demo.mock"})
@SpringBootTest(classes = {MockApplication.class})
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
public class MockApplicationTest {
}
