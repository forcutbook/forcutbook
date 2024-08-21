package konkuk.forcutbook.diary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import konkuk.forcutbook.diary.dto.DiaryFeedResDto;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;
import konkuk.forcutbook.domain.user.QUser;
import konkuk.forcutbook.friend.domain.QFriend;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static konkuk.forcutbook.diary.domain.QDiary.diary;
import static konkuk.forcutbook.diary.domain.QDiaryImage.diaryImage;
import static konkuk.forcutbook.domain.user.QUser.user;
import static konkuk.forcutbook.friend.domain.QFriend.friend;

@RequiredArgsConstructor
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public List<DiaryListEachResDto> findDiaryListDtoByWriterId(Long userId) {
        return query
                .select(Projections.constructor(DiaryListEachResDto.class,
                        diary.id.as("diaryId"),
                        diary.title,
                        diary.content,
                        diaryImage.imageUrl,
                        diary.createdAt))
                .from(diary)
                .leftJoin(diary.diaryImages, diaryImage)
                .where(diary.writer.id.eq(userId)
                        .and(diaryImage.isNull().or(diaryImage.id.eq(
                                JPAExpressions.select(diaryImage.id.min())
                                        .from(diaryImage)
                                        .where(diaryImage.diary.eq(diary))))))
                .fetch();
    }

    @Override
    public List<DiaryFeedResDto> findDiaryListForFeed(Long userId) {
        return query
                .select(Projections.constructor(DiaryFeedResDto.class,
                        user.id.as("userId"),
                        user.userName,
                        diary.id.as("diaryId"),
                        diary.title,
                        diary.content,
                        diaryImage.imageUrl,
                        diary.createdAt))
                .from(diary)
                .join(diary.writer, user)
                .leftJoin(diary.diaryImages, diaryImage)
                .where(
                        diary.writer.id.in(
                                        JPAExpressions.select(friend.receiver.id)
                                                .from(friend)
                                                .where(friend.sender.id.eq(userId)
                                                        .and(friend.isAccept.eq(true))),
                                        Expressions.asNumber(userId))
                                .and(diaryImage.isNull()
                                        .or(diaryImage.id.eq(
                                                JPAExpressions.select(diaryImage.id.min())
                                                        .from(diaryImage)
                                                        .where(diaryImage.diary.eq(diary))))))
                .orderBy(diary.createdAt.desc())
                .fetch();
    }
}
