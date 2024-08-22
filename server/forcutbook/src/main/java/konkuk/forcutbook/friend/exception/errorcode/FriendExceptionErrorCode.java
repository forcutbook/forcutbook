package konkuk.forcutbook.friend.exception.errorcode;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum FriendExceptionErrorCode implements ResponseStatus {
    DUPLICATED_FRIEND(5000, HttpStatus.BAD_REQUEST.value(), "중복된 친구 요청입니다.");

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
