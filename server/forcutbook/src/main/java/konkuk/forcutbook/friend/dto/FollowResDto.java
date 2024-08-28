package konkuk.forcutbook.friend.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowResDto {
    private Long userId;
    private String userName;
    private String imageUrl;
    private FriendStatus status;
    private LocalDateTime createdAt;

    @QueryProjection
    public FollowResDto(Long userId, String userName, String imageUrl, boolean isAccept, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.status = isAccept ? FriendStatus.FOLLOWING : FriendStatus.UNFOLLOING;
        this.createdAt = createdAt;
    }
}
