package konkuk.forcutbook.domain.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import konkuk.forcutbook.domain.user.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiSignupResponse> createUser(@RequestBody ApiSignupRequest apiSignupRequest) {
        long userId = userService.createUser(apiSignupRequest);
        ApiSignupResponse response = new ApiSignupResponse(1000, 200, "요청에 성공하였습니다.", userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiLoginResponse> login(@RequestBody ApiLoginRequest loginRequest, HttpServletRequest request) {
        return userService.login(loginRequest)
                .map(user -> {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", user.getId());
                    return new ResponseEntity<>(new ApiLoginResponse(1000, 200, "로그인에 성공하였습니다.", user.getId()), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(new ApiLoginResponse(1001, 401, "로그인에 실패하였습니다.", null), HttpStatus.UNAUTHORIZED));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<ApiLogoutResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            ApiLogoutResponse response = new ApiLogoutResponse(1000, 200, "로그아웃에 성공하였습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiLogoutResponse response = new ApiLogoutResponse(1001, 400, "로그아웃에 실패하였습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
