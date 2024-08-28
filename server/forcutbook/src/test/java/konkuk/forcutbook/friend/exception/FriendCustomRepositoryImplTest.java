package konkuk.forcutbook.friend.exception;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.friend.FriendRepository;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.friend.dto.FollowResDto;
import konkuk.forcutbook.friend.dto.FriendStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class FriendCustomRepositoryImplTest {

    @Autowired
    EntityManager em;
    @Autowired
    FriendRepository friendRepository;

    @Test
    void findFollowingListDto() {
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
        List<FollowResDto> result = friendRepository.findFollowingListDto(user.getId(), friend.getId());
        assertThat(result).extracting("userId").contains(following1.getId(), following2.getId());
        assertThat(result).extracting("userName").contains(following1.getUserName(), following2.getUserName());
        assertThat(result).extracting("imageUrl").contains(following1.getImageUrl(), following2.getImageUrl());
        assertThat(result).extracting("status").contains(FriendStatus.FOLLOWING, FriendStatus.UNFOLLOING);
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword("test");
        return user;
    }
}