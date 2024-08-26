package konkuk.forcutbook.friend.dto;

import lombok.Getter;

@Getter
public enum FriendStatus {
    DEFAULT("요청"), REQUEST("요청"), ACCEPT("수락"), DENY("거절"), CANCEL("취소");

    FriendStatus(String message) {
        this.message = message;
    }

    private String message;
}
