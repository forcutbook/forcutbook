package konkuk.forcutbook.user.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiSignupResponse {
    private Long userId;

    public ApiSignupResponse(Long userId) {
        this.userId = userId;
    }
}