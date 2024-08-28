package konkuk.forcutbook.friend.dto;

import konkuk.forcutbook.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class FollowListResDto {
    private Long userId;
    private String userName;
    private String imageUrl;
    private List<FollowResDto> data;

    public FollowListResDto(User user, List<FollowResDto> data) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.imageUrl = user.getImageUrl();
        this.data = data;
    }
}
