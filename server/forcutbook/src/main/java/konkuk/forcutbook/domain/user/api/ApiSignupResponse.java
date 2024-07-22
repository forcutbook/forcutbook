package konkuk.forcutbook.domain.user.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiSignupResponse {
    private int code;
    private int status;
    private String message;
    private Long userId;

    public ApiSignupResponse(int code, int status, String message, Long userId) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.userId = userId;
    }
}
