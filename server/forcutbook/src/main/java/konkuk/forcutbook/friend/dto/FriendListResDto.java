package konkuk.forcutbook.friend.dto;

import konkuk.forcutbook.friend.domain.Friend;
import lombok.Getter;
import java.util.List;

@Getter
public class FriendListResDto {
    private final List<FriendResDto> data;

    private FriendListResDto(List<FriendResDto> data) {
        this.data = data;
    }

    public static FriendListResDto toDtoBySender(List<Friend> friends) {
        List<FriendResDto> dtoList = friends.stream()
                .map(FriendResDto::toDtoBySender)
                .toList();
        return new FriendListResDto(dtoList);
    }

    public static FriendListResDto toDtoByReceiver(List<Friend> friends) {
        List<FriendResDto> dtoList = friends.stream()
                .map(FriendResDto::toDtoByReceiver)
                .toList();
        return new FriendListResDto(dtoList);
    }
}
