package konkuk.forcutbook.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import konkuk.forcutbook.user.api.*;
import konkuk.forcutbook.user.api.ApiLoginRequest;
import konkuk.forcutbook.user.api.ApiLoginResponse;
import konkuk.forcutbook.user.api.ApiSignupRequest;
import konkuk.forcutbook.user.api.ApiSignupResponse;
import konkuk.forcutbook.user.dto.GetUserInfoDTO;
import konkuk.forcutbook.user.dto.GetUserListDTO;
import konkuk.forcutbook.user.exception.UserException;
import konkuk.forcutbook.user.exception.errorcode.UserExceptionErrorcode;
import konkuk.forcutbook.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/{friendId}")
    public BaseResponse<GetUserInfoDTO> getUserInfo(@PathVariable Long userId, @PathVariable Long friendId) {
        GetUserInfoDTO response = userService.getUserInfo(userId, friendId);
        return new BaseResponse<>(response);
    }

    @GetMapping("/users/{userId}")
    public BaseResponse<List<GetUserListDTO>> getUsersBySearchWord(@PathVariable Long userId, @RequestParam("search") String searchWord){
        List<GetUserListDTO> response = userService.getUsersBySearchWord(userId, searchWord);
        return new BaseResponse<>(response);
    }

    @PostMapping("/users")
    public BaseResponse<ApiSignupResponse> createUser(@RequestBody ApiSignupRequest apiSignupRequest) {
        long userId = userService.createUser(apiSignupRequest);
        ApiSignupResponse response = new ApiSignupResponse(userId);
        return new BaseResponse<>(response);
    }

    @PostMapping("/auth/login")
    public BaseResponse<ApiLoginResponse> login(@RequestBody ApiLoginRequest loginRequest, HttpServletRequest request) {
        return userService.login(loginRequest)
                .map(user -> {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", user.getId());
                    return new BaseResponse<>(new ApiLoginResponse(user.getId()));
                })
                .orElseGet(() -> {throw new UserException(UserExceptionErrorcode.FAILED_LOGIN);});
    }

    @PostMapping("/auth/logout")
    public BaseResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return new BaseResponse<>();
        } else {
            throw new UserException(UserExceptionErrorcode.FAILED_LOGOUT);
        }
    }
}