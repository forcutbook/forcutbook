package konkuk.forcutbook.friend.dto;

import konkuk.forcutbook.friend.domain.Friend;
import lombok.Getter;
import java.util.List;

@Getter
public class FriendAcceptListResDto {
    private final List<FriendAcceptResDto> data;

    private FriendAcceptListResDto(List<FriendAcceptResDto> data) {
        this.data = data;
    }

    public static FriendAcceptListResDto toDto(List<Friend> friends) {
        List<FriendAcceptResDto> dtoList = friends.stream()
                .map(FriendAcceptResDto::toDto)
                .toList();
        return new FriendAcceptListResDto(dtoList);
    }
}
