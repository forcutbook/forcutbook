package konkuk.forcutbook.domain.user.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiSignupRequest {
    private String userName;
    private String password;
}
