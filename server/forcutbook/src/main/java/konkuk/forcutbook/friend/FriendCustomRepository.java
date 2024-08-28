package konkuk.forcutbook.friend;

import konkuk.forcutbook.friend.dto.FollowResDto;

import java.util.List;

public interface FriendCustomRepository {
    List<FollowResDto> findFollowingListDto(Long userId, Long friendId);
}
