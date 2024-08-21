package konkuk.forcutbook.global.oauth.exception.handler;

import konkuk.forcutbook.global.oauth.exception.OAuthException;
import konkuk.forcutbook.global.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OAuthExceptionHandler {
    @ExceptionHandler(OAuthException.class)
    public BaseErrorResponse handleOAuthException(OAuthException e) {
        log.error("[OAuth exception: handle_OAuthExceptionHandler]", e);
        return new BaseErrorResponse(e.getResponseStatus(), e.getMessage());
    }
}
