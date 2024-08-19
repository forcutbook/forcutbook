package konkuk.forcutbook.friend;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.friend.dto.FriendListResDto;
import konkuk.forcutbook.friend.dto.FriendResDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class FriendServiceIntegrationTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendService friendService;

    @DisplayName("친구 요청")
    @Test
    void requestFriend() {
        //given
        User sender = createUser("sender");
        User receiver = createUser("receiver");
        userRepository.save(sender);
        userRepository.save(receiver);

        em.flush();
        em.clear();

        //when
        Long friendId = friendService.requestFriend(sender.getId(), receiver.getId());
        Friend friend = friendRepository.findById(friendId).orElse(null);

        //then
        assertThat(friend).isNotNull();
        assertThat(friend.getSender().getId()).isEqualTo(sender.getId());
        assertThat(friend.getReceiver().getId()).isEqualTo(receiver.getId());
        assertThat(friend.isAccept()).isFalse();
    }

    @DisplayName("친구 수락")
    @Test
    void acceptFriend() {
        //given
        User sender = createUser("sender");
        User receiver = createUser("receiver");
        userRepository.save(sender);
        userRepository.save(receiver);

        Friend friend = Friend.createFriend(sender, receiver);
        friendRepository.save(friend);

        em.flush();
        em.clear();

        //when
        Long id = friendService.acceptFriend(receiver.getId(), sender.getId());
        Friend findFriend = friendRepository.findById(id).orElse(null);

        //then
        assertThat(findFriend).isNotNull();
        assertThat(findFriend.isAccept()).isTrue();
    }

    @Test
    @DisplayName("친구 수락 대기 리스트 조회")
    void getFriendAcceptList() {
        //given
        User sender1 = createUser("sender1");
        User sender2 = createUser("sender2");
        User receiver = createUser("receiver");
        userRepository.save(sender1);
        userRepository.save(sender2);
        userRepository.save(receiver);

        Friend friend1 = Friend.createFriend(sender1, receiver);
        friendRepository.save(friend1);
        Friend friend2 = Friend.createFriend(sender2, receiver);
        friendRepository.save(friend2);

        em.flush();
        em.clear();

        //when
        FriendListResDto dto = friendService.getFriendAcceptList(receiver.getId());

        //then
        List<FriendResDto> data = dto.getData();
        assertThat(data.size()).isEqualTo(2);
        assertThat(data).extracting("userId").contains(sender1.getId(), sender2.getId());
        assertThat(data).extracting("userName").contains(sender1.getUserName(), sender2.getUserName());
        assertThat(data).extracting("createdAt").contains(friend1.getCreatedAt(), friend2.getCreatedAt());
    }

    @Test
    @DisplayName("팔로워 목록 조회")
    void getFollowerList() {
        //given
        User sender1 = createUser("sender1");
        User sender2 = createUser("sender2");
        User waitSender = createUser("waitSender");
        User receiver = createUser("receiver");
        userRepository.save(sender1);
        userRepository.save(sender2);
        userRepository.save(waitSender);
        userRepository.save(receiver);

        Friend friend1 = Friend.createFriend(sender1, receiver);
        friend1.setAccept(true);
        friendRepository.save(friend1);

        Friend friend2 = Friend.createFriend(sender2, receiver);
        friend2.setAccept(true);
        friendRepository.save(friend2);

        Friend waitFriend = Friend.createFriend(waitSender, receiver);
        friendRepository.save(waitFriend);

        //when
        FriendListResDto dto = friendService.getFollowerList(receiver.getId());

        //then
        List<FriendResDto> data = dto.getData();
        assertThat(data.size()).isEqualTo(2);
        assertThat(data).extracting("userId").contains(sender1.getId(), sender2.getId());
        assertThat(data).extracting("userName").contains(sender1.getUserName(), sender2.getUserName());
        assertThat(data).extracting("createdAt").contains(friend1.getCreatedAt(), friend2.getCreatedAt());
    }

    @Test
    @DisplayName("팔로잉 목록 조회")
    void getFollowingList() {
        //given
        User sender = createUser("sender");
        User receiver1 = createUser("receiver1");
        User receiver2 = createUser("receiver2");
        User waitReceiver = createUser("waitReceiver");
        userRepository.save(sender);
        userRepository.save(receiver1);
        userRepository.save(receiver2);
        userRepository.save(waitReceiver);

        Friend friend1 = Friend.createFriend(sender, receiver1);
        friend1.setAccept(true);
        friendRepository.save(friend1);

        Friend friend2 = Friend.createFriend(sender, receiver2);
        friend2.setAccept(true);
        friendRepository.save(friend2);

        Friend waitFriend = Friend.createFriend(sender, waitReceiver);
        friendRepository.save(waitFriend);

        //when
        FriendListResDto dto = friendService.getFollowingList(sender.getId());

        //then
        List<FriendResDto> data = dto.getData();
        assertThat(data.size()).isEqualTo(2);
        assertThat(data).extracting("userId").contains(receiver1.getId(), receiver2.getId());
        assertThat(data).extracting("userName").contains(receiver1.getUserName(), receiver2.getUserName());
        assertThat(data).extracting("createdAt").contains(friend1.getCreatedAt(), friend2.getCreatedAt());
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword("test");
        return user;
    }
}