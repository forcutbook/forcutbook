package konkuk.forcutbook.friend;

import konkuk.forcutbook.user.User;
import konkuk.forcutbook.user.UserRepository;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.friend.dto.FollowListResDto;
import konkuk.forcutbook.friend.dto.FollowResDto;
import konkuk.forcutbook.friend.dto.FriendListResDto;
import konkuk.forcutbook.friend.exception.FriendException;
import konkuk.forcutbook.friend.exception.errorcode.FriendExceptionErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long requestFriend(Long userId, Long friendId){
        //검증 로직
        User sender = findUser(userId);
        User receiver = findUser(friendId);
        checkAlreadyFriend(userId, friendId);

        //서비스 로직
        Friend friend = Friend.createFriend(sender, receiver);
        friendRepository.save(friend);

        return friend.getId();
    }

    @Transactional
    public Long cancelFriendRequest(Long userId, Long friendId) {
        //검증 로직
        checkExistUser(userId, friendId);
        Friend friendShip = checkExistFriendRequest(userId, friendId);

        //서비스 로직
        friendRepository.delete(friendShip);
        return friendShip.getId();
    }

    @Transactional
    public Long acceptFriend(Long userId, Long senderId){
        //검증 로직
        checkExistUser(userId, senderId);
        Friend friend = checkExistFriendRequest(senderId, userId);

        //서비스 로직
        friend.setAccept(true);
        return friend.getId();
    }

    @Transactional
    public Long denyFriend(Long userId, Long friendId) {
        //검증 로직
        checkExistUser(userId, friendId);
        Friend friendShip = checkExistFriendRequest(friendId, userId);

        //서비스 로직
        friendRepository.delete(friendShip);
        return friendShip.getId();
    }

    @Transactional
    public Long deleteFollowing(Long userId, Long friendId) {
        //검증 로직
        checkExistUser(userId, friendId);
        Friend friendShip = checkIsFriendShip(userId, friendId);

        //서비스 로직
        friendRepository.delete(friendShip);
        return friendShip.getId();
    }

    @Transactional
    public Long deleteFollower(Long userId, Long friendId) {
        //검증 로직
        checkExistUser(userId, friendId);
        Friend friendShip = checkIsFriendShip(friendId, userId);

        //서비스 로직
        friendRepository.delete(friendShip);
        return friendShip.getId();
    }

    public FriendListResDto getFriendAcceptList(Long userId){
        //검증 로직
        checkExistUser(userId);

        //서비스 로직
        List<Friend> acceptList = friendRepository.findByReceiverIdAndIsAccept(userId, false);

        return FriendListResDto.toDtoBySender(acceptList);
    }

    public FollowListResDto getFollowerList(Long userId, Long friendId) {
        //검증 로직
        checkExistUser(userId, friendId);
        if (userId != friendId){
            checkIsFriendShip(userId, friendId);
        }

        //서비스 로직
        List<FollowResDto> followerListDto = friendRepository.findFollowerListDto(userId, friendId);

        return new FollowListResDto(userId, followerListDto);
    }

    public FollowListResDto getFollowingList(Long userId, Long friendId) {
        //검증 로직
        checkExistUser(userId, friendId);
        if (userId != friendId){
            checkIsFriendShip(userId, friendId);
        }

        //서비스 로직
        List<FollowResDto> followingListDto = friendRepository.findFollowingListDto(userId, friendId);

        return new FollowListResDto(userId, followingListDto);
    }


    private User findUser(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new FriendException(FriendExceptionErrorCode.NO_SUCH_USER);
        }
        return user;
    }

    private void checkExistUser(Long... userId){
        for (Long id : userId) {
            boolean isExist = userRepository.existsById(id);
            if (!isExist){
                throw new FriendException(FriendExceptionErrorCode.NO_SUCH_USER);
            }
        }
    }

    private void checkAlreadyFriend(Long senderId, Long receiver){
        if(friendRepository.existsBySenderIdAndReceiverId(senderId, receiver)){
            throw new FriendException(FriendExceptionErrorCode.ALREADY_FRIEND);
        }
    }

    private Friend checkIsFriendShip(Long senderId, Long receiverId){
        Friend friend = friendRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow();
        if (!friend.isAccept()){
            throw new FriendException(FriendExceptionErrorCode.NO_SUCH_FRIEND);
        }
        return friend;
    }

    private Friend checkExistFriendRequest(Long senderId, Long receiverId) {
        Friend friend = friendRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElse(null);
        if (friend == null) {
            throw new FriendException(FriendExceptionErrorCode.NO_SUCH_FRIEND_REQUEST);
        }
        if (friend.isAccept()){
            throw new FriendException(FriendExceptionErrorCode.ALREADY_FRIEND);
        }
        return friend;
    }
}
