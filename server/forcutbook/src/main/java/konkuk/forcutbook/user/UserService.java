package konkuk.forcutbook.user;

import konkuk.forcutbook.diary.repository.DiaryRepository;
import konkuk.forcutbook.user.api.ApiLoginRequest;
import konkuk.forcutbook.user.api.ApiSignupRequest;
import konkuk.forcutbook.user.dto.GetUserInfoDTO;
import konkuk.forcutbook.user.dto.GetUserListDTO;
import konkuk.forcutbook.user.exception.UserException;
import konkuk.forcutbook.user.exception.errorcode.UserExceptionErrorcode;
import konkuk.forcutbook.friend.FriendRepository;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.global.domain.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    public long createUser(ApiSignupRequest apiSignupRequest) {
        log.info("Creating user: {}", apiSignupRequest.getUserName());

        User user = new User();
        user.setUserName(apiSignupRequest.getUserName());
        user.setPassword(apiSignupRequest.getPassword());

        user = userRepository.save(user);
        return user.getId();
    }

    public Optional<User> login(ApiLoginRequest loginRequest) {
        return userRepository.findByUserNameAndPassword(loginRequest.getUserName(), loginRequest.getPassword());
    }

    public List<GetUserListDTO> getUsersBySearchWord(long userId, String userName) {
        log.info("Getting users by search word: {}", userName);
        List<User> users = userRepository.getUserByUserName(userName);
        List<GetUserListDTO> userListDTO = new ArrayList<>();
        for (User user : users) {
            log.info("User: {}", user.getUserName());
            // friend 관계를 조회
            String status = getFriendStatus(userId, user.getId());
            // User를 DTO로 변환
            GetUserListDTO userDTO = userConvertToDTO(user, status);
            userListDTO.add(userDTO);
        }
        return userListDTO;
    }
    private String getFriendStatus(long userId, long friendId) {
        Optional<Friend> friendOptional = friendRepository.findBySenderIdAndReceiverId(userId, friendId);

        String status;
        if (friendOptional.isPresent()) {
            Friend friend = friendOptional.get();
            if (friend.isAccept()) {
                status = "구독중";  // 친구 관계가 수락된 경우
            } else {
                status = "요청중";  // 친구 요청이 있으나 수락되지 않은 경우
            }
        } else {
            status = "구독";  // 친구 관계가 없는 경우
        }
        return status;
    }

    private GetUserListDTO userConvertToDTO(User user, String status) {

        return GetUserListDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .imageUrl(user.getImageUrl())
                .status(status)
                .build();
    }

    public GetUserInfoDTO getUserInfo(Long userId, Long friendId) {
        User user = userRepository.findById(friendId).orElse(null);
        if (user == null) {
            throw new UserException(UserExceptionErrorcode.NOT_FOUND_USER);
        }
        Long diaryCnt = diaryRepository.countByWriterIdAndStatus(friendId, Status.ACTIVE);
        Long following = friendRepository.countBySenderIdAndIsAccept(friendId, true);
        Long follower = friendRepository.countByReceiverIdAndIsAccept(friendId, true);
        String status = getFriendStatus(userId, friendId);
        return GetUserInfoDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .diaryCnt(diaryCnt)
                .followerCnt(follower)
                .subscriptionCnt(following)
                .friendRequestStatus(status)
                .build();
    }
}
