package demo.mock.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import demo.mock.MockApplicationTest;
import demo.mock.repository.UserRepository;
import demo.mock.util.EasyExcelUtil;
import demo.mock.vo.UserVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * UserServiceTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@Slf4j
@PrepareForTest({EasyExcelUtil.class})
public class UserServiceTest extends MockApplicationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @SpyBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @SneakyThrows
    public void getUserName() {
        PowerMockito.doReturn(null).when(userRepository).getById(Mockito.anyString());
        UserVo userName = userService.getUserName("1");
        Assert.assertEquals(userName.getName(),"name");
    }
}
