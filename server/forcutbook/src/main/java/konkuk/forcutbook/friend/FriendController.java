package konkuk.forcutbook.friend;

import konkuk.forcutbook.friend.dto.FriendListResDto;
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

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{friendId}/accept")
    public ResponseEntity<BaseResponse> acceptFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.acceptFriend(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{friendId}/deny")
    public ResponseEntity<BaseResponse> denyFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.denyFriend(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{friendId}/deleted")
    public ResponseEntity<BaseResponse> deleteFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.deleteFriend(userId, friendId);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/accept")
    public ResponseEntity<BaseResponse> getFriendAcceptList(@PathVariable Long userId){
        FriendListResDto resultDto = friendService.getFriendAcceptList(userId);

        BaseResponse<FriendListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/follower")
    public ResponseEntity<BaseResponse> getFollowerList(@PathVariable Long userId){
        FriendListResDto resultDto = friendService.getFollowerList(userId);

        BaseResponse<FriendListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/following")
    public ResponseEntity<BaseResponse> getFollowingList(@PathVariable Long userId){
        FriendListResDto resultDto = friendService.getFollowingList(userId);

        BaseResponse<FriendListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }
}
