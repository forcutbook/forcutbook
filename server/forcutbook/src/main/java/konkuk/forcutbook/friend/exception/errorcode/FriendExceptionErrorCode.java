package konkuk.forcutbook.friend.exception.errorcode;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum FriendExceptionErrorCode implements ResponseStatus {
    NO_SUCH_USER(2000, HttpStatus.NOT_FOUND.value(), "요청한 사용자 정보가 존재하지 않습니다."),
    NO_SUCH_FRIEND_REQUEST(2000, HttpStatus.BAD_REQUEST.value(), "해당되는 친구 요청이 존재하지 않습니다."),
    NO_SUCH_FRIEND(2000, HttpStatus.BAD_REQUEST.value(), "해당되는 친구 정보가 존재하지 않습니다."),
    ALREADY_FRIEND(2000, HttpStatus.BAD_REQUEST.value(), "이미 친구 관계입니다.");

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
