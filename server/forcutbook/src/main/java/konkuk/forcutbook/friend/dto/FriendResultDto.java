package konkuk.forcutbook.friend.dto;

import lombok.Getter;

@Getter
public class FriendResultDto {
    private Long id;
    private String status;

    public FriendResultDto(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
