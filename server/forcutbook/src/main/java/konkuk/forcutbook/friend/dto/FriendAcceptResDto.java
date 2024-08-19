package konkuk.forcutbook.friend.dto;

import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.friend.domain.Friend;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendAcceptResDto {
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;

    private FriendAcceptResDto(Long userId, String userName, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public static FriendAcceptResDto toDto(User user, LocalDateTime createdAt){
        return new FriendAcceptResDto(user.getId(), user.getUserName(), createdAt);
    }

    public static FriendAcceptResDto toDto(Friend friend){
        User sender = friend.getSender();
        return new FriendAcceptResDto(sender.getId(), sender.getUserName(), friend.getCreatedAt());
    }
}
