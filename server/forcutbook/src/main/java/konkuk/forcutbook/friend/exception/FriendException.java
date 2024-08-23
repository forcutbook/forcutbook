package konkuk.forcutbook.friend.exception;

import konkuk.forcutbook.global.response.ResponseStatus;

public class FriendException extends RuntimeException{
    private final ResponseStatus responseStatus;

    public FriendException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
