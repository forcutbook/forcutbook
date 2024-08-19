package konkuk.forcutbook.friend;

import konkuk.global.dto.SuccessResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
