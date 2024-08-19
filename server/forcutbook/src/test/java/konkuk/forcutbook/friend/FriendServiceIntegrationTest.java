package konkuk.forcutbook.friend;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import konkuk.forcutbook.friend.domain.Friend;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword("test");
        return user;
    }
}