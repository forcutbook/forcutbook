package konkuk.forcutbook.friend.exception;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import konkuk.forcutbook.friend.FriendCustomRepository;
import konkuk.forcutbook.friend.domain.QFriend;
import konkuk.forcutbook.friend.dto.FollowResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static konkuk.forcutbook.friend.domain.QFriend.friend;

@Slf4j
@RequiredArgsConstructor
public class FriendCustomRepositoryImpl implements FriendCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<FollowResDto> findFollowingListDto(Long userId, Long friendId) {
        QFriend subFriend = new QFriend("subFriend");
        return query
                .select(Projections.constructor(FollowResDto.class,
                        friend.receiver.id.as("userId"),
                        friend.receiver.userName,
                        friend.receiver.imageUrl,
                        subFriend.isAccept,
                        friend.createdAt
                ))
                .from(friend)
                .leftJoin(subFriend)
                .on(friend.receiver.id.eq(subFriend.receiver.id),
                        subFriend.sender.id.eq(userId))
                .where(friend.sender.id.eq(friendId),
                        friend.isAccept.isTrue())
                .fetch();
    }
}
