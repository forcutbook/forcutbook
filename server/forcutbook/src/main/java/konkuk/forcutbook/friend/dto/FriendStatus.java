package konkuk.forcutbook.friend.dto;

import lombok.Getter;

@Getter
public enum FriendStatus {
    DEFAULT("요청"), REQUEST("요청"), ACCEPT("수락"), DENY("거절"), CANCEL("취소"), FOLLOWING("구독중"), UNFOLLOING("구독");

    FriendStatus(String message) {
        this.message = message;
    }

    private String message;
}
