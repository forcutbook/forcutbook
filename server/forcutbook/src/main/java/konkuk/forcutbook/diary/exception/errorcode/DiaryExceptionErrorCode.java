package konkuk.forcutbook.diary.exception.errorcode;

import konkuk.forcutbook.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum DiaryExceptionErrorCode implements ResponseStatus {
    NO_SUCH_USER(2000, HttpStatus.NOT_FOUND.value(), "요청한 사용자 정보가 존재하지 않습니다."),
    NO_SUCH_DIARY(2000, HttpStatus.NOT_FOUND.value(), "요청한 다이어리 정보가 존재하지 않습니다."),
    NO_AUTHORITY_DIARY(2000, HttpStatus.NOT_FOUND.value(), "다이어리 접근 권한이 존재하지 않습니다."),
    NO_AUTHORITY_FRIEND(2000, HttpStatus.FORBIDDEN.value(), "친구 접근 권한이 없습니다."),
    NO_DIARY_CREATE_IMAGE(2000, HttpStatus.NOT_FOUND.value(), "자동 생성을 하기 위한 이미지가 없습니다.");

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
