package konkuk.forcutbook.domain.user.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiLoginResponse {
    private Long userId;

    public ApiLoginResponse(Long userId) {
        this.userId = userId;
    }
}
