package konkuk.forcutbook.friend;

import konkuk.forcutbook.friend.dto.FriendListResDto;
import konkuk.global.dto.SuccessResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/users/{userId}/friends")
@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/{friendId}/request")
    public ResponseEntity<SuccessResDto> requestFriend(@PathVariable Long userId,
                                                       @PathVariable Long friendId){
        Long id = friendService.requestFriend(userId, friendId);

        return SuccessResDto.getResponseEntity(id, SuccessResDto.SuccessMessage.REQUEST_SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/{friendId}/accept")
    public ResponseEntity<SuccessResDto> acceptFriend(@PathVariable Long userId,
                                                      @PathVariable Long friendId){
        Long id = friendService.acceptFriend(userId, friendId);

        return SuccessResDto.getResponseEntity(id, SuccessResDto.SuccessMessage.ACCEPT_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/accept")
    public ResponseEntity<FriendListResDto> getFriendAcceptList(@PathVariable Long userId){
        FriendListResDto dto = friendService.getFriendAcceptList(userId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/follower")
    public ResponseEntity<FriendListResDto> getFollowerList(@PathVariable Long userId){
        FriendListResDto dto = friendService.getFollowerList(userId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
