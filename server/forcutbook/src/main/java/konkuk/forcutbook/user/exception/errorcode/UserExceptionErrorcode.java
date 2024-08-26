package konkuk.forcutbook.user.exception.errorcode;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserExceptionErrorcode implements ResponseStatus {
    FAILED_LOGIN(4000, HttpStatus.BAD_REQUEST.value(), "로그인 실패하였습니다."),
    FAILED_LOGOUT(4001, HttpStatus.BAD_REQUEST.value(), "로그아웃 실패하였습니다."),
    NOT_FOUND_USER(4002, HttpStatus.BAD_REQUEST.value(), "해당유저를 찾을 수 없습니다.");

    private final int code;
    private final int status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
