package konkuk.forcutbook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserInfoDTO {
    private long userId;
    private String userName;
    private long subscriptionCnt;
    private long followerCnt;
    private long diaryCnt;
    private String friendRequestStatus;
}
