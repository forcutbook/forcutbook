package konkuk.forcutbook.friend.dto;

import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.friend.domain.Friend;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendResDto {
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;

    private FriendResDto(Long userId, String userName, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public static FriendResDto toDto(User user, LocalDateTime createdAt){
        return new FriendResDto(user.getId(), user.getUserName(), createdAt);
    }

    public static FriendResDto toDto(Friend friend){
        User sender = friend.getSender();
        return new FriendResDto(sender.getId(), sender.getUserName(), friend.getCreatedAt());
    }
}
