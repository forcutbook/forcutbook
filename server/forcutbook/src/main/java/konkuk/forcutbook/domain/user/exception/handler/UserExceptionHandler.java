package konkuk.forcutbook.domain.user.exception.handler;

import konkuk.forcutbook.domain.user.exception.UserException;
import konkuk.forcutbook.global.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserException.class)
    public BaseErrorResponse handleUserException(UserException e) {
        log.error("UserException: ", e);
        return new BaseErrorResponse(e.getResponseStatus(), e.getMessage());
    }
}
