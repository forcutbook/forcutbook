package konkuk.forcutbook.diary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.domain.QDiary;
import konkuk.forcutbook.diary.domain.QDiaryImage;
import konkuk.forcutbook.diary.dto.DiaryFeedResDto;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;
import konkuk.forcutbook.global.domain.Status;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static konkuk.forcutbook.diary.domain.QDiary.diary;
import static konkuk.forcutbook.diary.domain.QDiaryImage.diaryImage;
import static konkuk.forcutbook.user.QUser.user;
import static konkuk.forcutbook.friend.domain.QFriend.friend;

@RequiredArgsConstructor
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public List<DiaryListEachResDto> findDiaryListDtoByWriterId(Long userId) {
        QDiaryImage diaryImageSub = new QDiaryImage("diaryImageSub");
        return query
                .select(Projections.constructor(DiaryListEachResDto.class,
                        diary.writer.id.as("userId"),
                        diary.writer.userName,
                        diary.writer.imageUrl.as("userProfile"),
                        diary.id.as("diaryId"),
                        diary.title,
                        diary.content,
                        diaryImage.imageUrl,
                        diary.createdAt))
                .from(diary)
                .leftJoin(diary.diaryImages, diaryImage)
                .where(diary.writer.id.eq(userId)
                        .and(diaryImage.isNull().or(diaryImage.id.eq(
                                JPAExpressions.select(diaryImageSub.id.min())
                                        .from(diaryImageSub)
                                        .where(diaryImageSub.diary.eq(diary)))))
                        .and(diary.status.eq(Status.ACTIVE)))
                .fetch();
    }

    @Override
    public List<DiaryFeedResDto> findDiaryListForFeed(Long userId) {
        QDiaryImage diaryImageSub = new QDiaryImage("diaryImageSub");
        return query
                .select(Projections.constructor(DiaryFeedResDto.class,
                        user.id.as("userId"),
                        user.userName,
                        user.imageUrl.as("userImageUrl"),
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
                                                        .and(friend.isAccept.eq(true))))
                                .or(diary.writer.id.eq(userId))
                                .and(diaryImage.isNull()
                                        .or(diaryImage.id.eq(
                                                JPAExpressions.select(diaryImageSub.id.min())
                                                        .from(diaryImageSub)
                                                        .where(diaryImageSub.diary.eq(diary)))))
                                .and(diary.status.eq(Status.ACTIVE)))
                .orderBy(diary.createdAt.desc())
                .fetch();
    }

    @Override
    public Optional<Diary> checkIsWriterFriend(Long diaryId, Long userId) {
        Diary findDiary = query
                .selectFrom(diary)
                .join(friend).on(diary.writer.id.eq(friend.receiver.id))
                .where(friend.sender.id.eq(userId)
                        .and(friend.isAccept.eq(true))
                        .and(diary.id.eq(diaryId)))
                .fetchOne();
        return Optional.ofNullable(findDiary);
    }
}
