package konkuk.forcutbook.friend;

import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import konkuk.forcutbook.friend.domain.Friend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        User user = findUser(userId);
        User sender = findUser(senderId);
        Friend friend = findExistFriendRequest(userId, senderId);

        //서비스 로직
        friend.setAccept(true);
        return friend.getId();
    }

    private User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow();
    }

    private void checkAlreadyFriend(Long userId, Long senderId){
        if(friendRepository.existsBySenderIdAndReceiverId(senderId, userId)){
            //TODO 나중에 오류 상세히 수정
            throw new IllegalArgumentException("중복 요청");
        }
    }

    private Friend findExistFriendRequest(Long userId, Long friendId){
        //TODO 나중에 오류 상세히 수정
        Friend friend = friendRepository.findBySenderIdAndReceiverId(friendId, userId).orElseThrow();
        if(friend.isAccept()){
            throw new IllegalArgumentException("이미 친구 관계");
        }

        return friend;
    }
}
