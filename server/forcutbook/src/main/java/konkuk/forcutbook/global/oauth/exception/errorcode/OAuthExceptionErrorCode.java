package konkuk.forcutbook.global.oauth.exception.errorcode;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
public enum OAuthExceptionErrorCode implements ResponseStatus {

    NOT_VALID_EMAIL(5000, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 이메일입니다.");

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
