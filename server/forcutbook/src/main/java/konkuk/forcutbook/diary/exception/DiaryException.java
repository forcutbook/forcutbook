package konkuk.forcutbook.diary.exception;

import konkuk.forcutbook.global.response.ResponseStatus;

public class DiaryException extends RuntimeException{
    private final ResponseStatus responseStatus;

    public DiaryException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
