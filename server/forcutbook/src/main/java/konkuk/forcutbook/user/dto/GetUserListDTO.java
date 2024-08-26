package konkuk.forcutbook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserListDTO {
    private long userId;
    private String userName;
    private String imageUrl;
    private String status;
}
