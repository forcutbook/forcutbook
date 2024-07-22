package konkuk.forcutbook.domain.user.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiLoginRequest {
    private String userName;
    private String password;
}
