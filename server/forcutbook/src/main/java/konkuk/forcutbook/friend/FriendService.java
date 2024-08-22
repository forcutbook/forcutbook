package konkuk.forcutbook.friend;

import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.friend.dto.FriendListResDto;
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
    public Long acceptFriend(Long userId, Long senderId){
        //검증 로직
        User receiver = findUser(userId);
        User sender = findUser(senderId);
        Friend friend = findExistFriendRequest(senderId, userId);

        //서비스 로직
        friend.setAccept(true);
        return friend.getId();
    }

    @Transactional
    public Long denyFriend(Long userId, Long friendId) {
        //검증 로직
        User user = findUser(userId);
        User friend = findUser(friendId);
        Friend friendShip = findExistFriendRequest(friendId, userId);

        //서비스 로직
        friendRepository.delete(friendShip);
        return friendShip.getId();
    }

    public Long deleteFriend(Long userId, Long friendId) {
        //검증 로직
        User user = findUser(userId);
        Friend friendShip = checkIsFriendShip(userId, friendId);

        //서비스 로직
        friendRepository.delete(friendShip);
        return friendShip.getId();
    }

    public FriendListResDto getFriendAcceptList(Long userId){
        //검증 로직
        User user = findUser(userId);

        //서비스 로직
        List<Friend> acceptList = friendRepository.findByReceiverIdAndIsAccept(userId, false);

        return FriendListResDto.toDtoBySender(acceptList);
    }

    public FriendListResDto getFollowerList(Long userId) {
        //검증 로직
        User user = findUser(userId);

        //서비스 로직
        List<Friend> friends = friendRepository.findByReceiverIdAndIsAccept(userId, true);

        return FriendListResDto.toDtoBySender(friends);
    }

    public FriendListResDto getFollowingList(Long userId) {
        //검증 로직
        User user = findUser(userId);

        //서비스 로직
        List<Friend> friends = friendRepository.findBySenderIdAndIsAccept(userId, true);

        return FriendListResDto.toDtoByReceiver(friends);
    }

    private User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow();
    }

    private void checkAlreadyFriend(Long senderId, Long receiver){
        if(friendRepository.existsBySenderIdAndReceiverId(senderId, receiver)){
            //TODO 나중에 오류 상세히 수정
            throw new IllegalArgumentException("중복 요청");
        }
    }

    private Friend checkIsFriendShip(Long userId, Long friendId){
        Friend friend = friendRepository.findBySenderIdAndReceiverId(userId, friendId).orElseThrow();
        if (!friend.isAccept()){
            throw new RuntimeException("친구관계 아닌데 삭제 요청");
        }
        return friend;
    }

    private Friend findExistFriendRequest(Long senderId, Long receiverId){
        //TODO 나중에 오류 상세히 수정
        Friend friend = friendRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow();
        if(friend.isAccept()){
            throw new IllegalArgumentException("이미 친구 관계");
        }



        return friend;
    }
}
