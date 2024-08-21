package konkuk.forcutbook.domain.user;

import konkuk.forcutbook.domain.user.api.ApiLoginRequest;
import konkuk.forcutbook.domain.user.api.ApiSignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public long createUser(ApiSignupRequest apiSignupRequest) {
        log.info("Creating user: {}", apiSignupRequest.getUserName());

        User user = new User();
        user.setUserName(apiSignupRequest.getUserName());
        user.setPassword(apiSignupRequest.getPassword());

        user = userRepository.save(user);
        return user.getId();
    }

    public Optional<User> login(ApiLoginRequest loginRequest) {
        return userRepository.findByUserNameAndPassword(loginRequest.getUserName(), loginRequest.getPassword());
    }
}
