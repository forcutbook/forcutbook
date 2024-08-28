package konkuk.forcutbook.friend;

import konkuk.forcutbook.friend.dto.FollowListResDto;
import konkuk.forcutbook.friend.dto.FollowResDto;
import konkuk.forcutbook.friend.dto.FriendListResDto;
import konkuk.forcutbook.friend.dto.FriendStatus;
import konkuk.forcutbook.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/users/{userId}/friends")
@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/{friendId}/request")
    public ResponseEntity<BaseResponse> requestFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.requestFriend(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id, FriendStatus.REQUEST);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{friendId}/request/cancel")
    public ResponseEntity<BaseResponse> cancelFriendRequest(@PathVariable Long userId,
                                                            @PathVariable Long friendId){
        Long id = friendService.cancelFriendRequest(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id, FriendStatus.CANCEL);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{friendId}/accept")
    public ResponseEntity<BaseResponse> acceptFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.acceptFriend(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id, FriendStatus.ACCEPT);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{friendId}/deny")
    public ResponseEntity<BaseResponse> denyFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.denyFriend(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id, FriendStatus.DENY);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{friendId}/following/deleted")
    public ResponseEntity<BaseResponse> deleteFollowing(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.deleteFollowing(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{friendId}/follower/deleted")
    public ResponseEntity<BaseResponse> deleteFollower(@PathVariable Long userId,
                                                        @PathVariable Long friendId){
        Long id = friendService.deleteFollower(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/accept")
    public ResponseEntity<BaseResponse> getFriendAcceptList(@PathVariable Long userId){
        FriendListResDto resultDto = friendService.getFriendAcceptList(userId);

        BaseResponse<FriendListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{friendId}/follower")
    public ResponseEntity<BaseResponse> getFollowerList(@PathVariable Long userId,
                                                        @PathVariable Long friendId){
        FollowListResDto resultDto = friendService.getFollowerList(userId, friendId);

        BaseResponse<FollowListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{friendId}/following")
    public ResponseEntity<BaseResponse> getFollowingList(@PathVariable Long userId,
                                                         @PathVariable Long friendId){
        FollowListResDto resultDto = friendService.getFollowingList(userId, friendId);

        BaseResponse<FollowListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }
}
