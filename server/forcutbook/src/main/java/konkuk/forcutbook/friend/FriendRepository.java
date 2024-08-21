package konkuk.forcutbook.friend;

import konkuk.forcutbook.friend.domain.Friend;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    Optional<Friend> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @EntityGraph(attributePaths = {"sender"})
    List<Friend> findByReceiverIdAndIsAccept(Long receiverId, boolean isAccept);

    @EntityGraph(attributePaths = {"receiver"})
    List<Friend> findBySenderIdAndIsAccept(Long senderId, boolean isAccept);

    boolean existsBySenderIdAndReceiverIdAndIsAccept(Long senderId, Long receiverId, boolean isAccept);
    Long countBySenderIdAndIsAccept(Long senderId, boolean isAccept);
    Long countByReceiverIdAndIsAccept(Long receiverId, boolean isAccept);
}
