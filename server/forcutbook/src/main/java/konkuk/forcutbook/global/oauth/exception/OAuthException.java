package konkuk.forcutbook.global.oauth.exception;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.Getter;

@Getter
public class OAuthException extends RuntimeException {
    private final ResponseStatus responseStatus;

    public OAuthException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
