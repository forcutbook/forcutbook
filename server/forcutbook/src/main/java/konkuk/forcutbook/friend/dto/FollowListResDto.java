package konkuk.forcutbook.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowListResDto {
    private Long userId;
    private List<FollowResDto> data;
}
