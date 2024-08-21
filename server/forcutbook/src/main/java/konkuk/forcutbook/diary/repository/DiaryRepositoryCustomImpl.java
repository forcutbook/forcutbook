package konkuk.forcutbook.diary.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static konkuk.forcutbook.diary.domain.QDiary.diary;
import static konkuk.forcutbook.diary.domain.QDiaryImage.diaryImage;

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
}
