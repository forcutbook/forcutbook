package konkuk.forcutbook.friend;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.friend.dto.*;
import konkuk.forcutbook.friend.exception.FriendException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("친구 요청 취소")
    void test() {
        //given
        User user = createUser("user");
        User friend = createUser("friend");
        userRepository.save(user);
        userRepository.save(friend);

        Friend friendShip = Friend.createFriend(user, friend);
        friendRepository.save(friendShip);

        em.flush();
        em.clear();

        //when
        friendService.cancelFriendRequest(user.getId(), friend.getId());
        em.flush();
        em.clear();

        //then
        assertThatThrownBy(() -> friendRepository.findById(friendShip.getId()).orElseThrow()).isInstanceOf(NoSuchElementException.class);

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
    @DisplayName("친구 거절")
    void denyFriend() {
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
        Long friendShipId = friendService.denyFriend(receiver.getId(), sender.getId());
        em.flush();
        em.clear();

        //then
        assertThatThrownBy(() -> friendRepository.findById(friendShipId).orElseThrow()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("팔로잉 삭제")
    void deleteFollowing() {
        //given
        User user = createUser("user");
        User friend = createUser("friend");
        userRepository.save(user);
        userRepository.save(friend);

        Friend friendShip = Friend.createFriend(user, friend);
        friendShip.setAccept(true);
        friendRepository.save(friendShip);

        em.flush();
        em.clear();

        //when
        Long friendShipId = friendService.deleteFollowing(user.getId(), friend.getId());
        em.flush();
        em.clear();

        //then
        assertThatThrownBy(() -> friendRepository.findById(friendShipId).orElseThrow()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("팔로워 삭제")
    void deleteFollower() {
        //given
        User user = createUser("user");
        User friend = createUser("friend");
        userRepository.save(user);
        userRepository.save(friend);

        Friend friendShip = Friend.createFriend(friend, user);
        friendShip.setAccept(true);
        friendRepository.save(friendShip);

        em.flush();
        em.clear();

        //when
        Long friendShipId = friendService.deleteFollower(user.getId(), friend.getId());
        em.flush();
        em.clear();

        //then
        assertThatThrownBy(() -> friendRepository.findById(friendShipId).orElseThrow()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("팔로워 삭제 - 친구 관계x")
    void deleteFollowerNotFriend() {
        //given
        User user = createUser("user");
        User friend = createUser("friend");
        userRepository.save(user);
        userRepository.save(friend);

        Friend friendShip = Friend.createFriend(friend, user);
        friendShip.setAccept(false);
        friendRepository.save(friendShip);

        em.flush();
        em.clear();

        //when
        assertThatThrownBy(() -> friendService.deleteFollower(user.getId(), friend.getId())).isInstanceOf(FriendException.class);
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
//        assertThat(data).extracting("createdAt").contains(friend1.getCreatedAt(), friend2.getCreatedAt());
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
//        assertThat(data).extracting("createdAt").contains(friend1.getCreatedAt(), friend2.getCreatedAt());
    }

    @Test
    @DisplayName("팔로잉 목록 조회")
    void getFollowingList() {
        //given
        User user = createUser("user");
        User friend = createUser("friend");
        User following1 = createUser("following1");
        User following2 = createUser("following2");
        em.persist(user);
        em.persist(friend);
        em.persist(following1);
        em.persist(following2);

        Friend friendShip1 = Friend.createFriend(user, friend);
        friendShip1.setAccept(true);
        em.persist(friendShip1);

        Friend friendShip2 = Friend.createFriend(friend, following1);
        friendShip2.setAccept(true);
        em.persist(friendShip2);

        Friend friendShip3 = Friend.createFriend(friend, following2);
        friendShip3.setAccept(true);
        em.persist(friendShip3);

        Friend friendShip4 = Friend.createFriend(user, following1);
        friendShip4.setAccept(true);
        em.persist(friendShip4);

        em.flush();
        em.clear();

        //when
        FollowListResDto result = friendService.getFollowingList(user.getId(), friend.getId());

        //then
        assertThat(result.getUserId()).isEqualTo(user.getId());

        List<FollowResDto> data = result.getData();
        assertThat(data).extracting("userId").contains(following1.getId(), following2.getId());
        assertThat(data).extracting("userName").contains(following1.getUserName(), following2.getUserName());
        assertThat(data).extracting("imageUrl").contains(following1.getImageUrl(), following2.getImageUrl());
        assertThat(data).extracting("status").contains(FriendStatus.FOLLOWING, FriendStatus.UNFOLLOING);
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword("test");
        return user;
    }
}