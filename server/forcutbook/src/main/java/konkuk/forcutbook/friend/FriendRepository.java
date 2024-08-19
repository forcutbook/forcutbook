package konkuk.forcutbook.friend;

import konkuk.forcutbook.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    Optional<Friend> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
