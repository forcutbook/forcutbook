package konkuk.forcutbook.user.exception;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final ResponseStatus responseStatus;

    public UserException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
