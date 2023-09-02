package demo.mock.service;

import org.springframework.stereotype.Component;

import demo.mock.entity.User;
import demo.mock.repository.UserRepository;
import demo.mock.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserService
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {
    /**
     * userRepository
     */
    private final UserRepository userRepository;

    /**
     * getUserName
     *
     * @param userId String
     * @return UserVo
     */
    public UserVo getUserName(String userId) {
        User user = userRepository.getById("1");
        if (user == null) {
            return UserVo.builder().name("name").address("address").build();
        }
        return UserVo.builder().name(user.getName()).address(user.getName() + "address").build();
    }
}
