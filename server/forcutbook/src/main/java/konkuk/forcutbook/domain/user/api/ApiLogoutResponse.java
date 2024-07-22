package konkuk.forcutbook.domain.user.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiLogoutResponse {
    private int code;
    private int status;
    private String message;

    public ApiLogoutResponse(int code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
