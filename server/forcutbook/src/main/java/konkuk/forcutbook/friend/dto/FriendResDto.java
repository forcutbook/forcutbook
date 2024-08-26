package konkuk.forcutbook.friend.dto;

import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.friend.domain.Friend;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendResDto {
    private Long userId;
    private String userName;
    private String imageUrl;
    private LocalDateTime createdAt;

    public FriendResDto(Long userId, String userName, String imageUrl, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public static FriendResDto toDto(User user, LocalDateTime createdAt){
        return new FriendResDto(user.getId(), user.getUserName(), user.getImageUrl(), createdAt);
    }

    public static FriendResDto toDtoBySender(Friend friend){
        User sender = friend.getSender();
        return new FriendResDto(sender.getId(), sender.getUserName(), sender.getImageUrl(), friend.getCreatedAt());
    }

    public static FriendResDto toDtoByReceiver(Friend friend){
        User receiver = friend.getReceiver();
        return new FriendResDto(receiver.getId(), receiver.getUserName(), receiver.getImageUrl(), friend.getCreatedAt());
    }
}
