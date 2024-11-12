package konkuk.forcutbook.diary.repository;

import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.DiaryFeedResDto;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;

import java.util.List;
import java.util.Optional;

public interface DiaryRepositoryCustom {
    List<DiaryListEachResDto> findDiaryListDtoByWriterId(Long userId);
    List<DiaryFeedResDto> findDiaryListForFeed(Long userId);
    Optional<Diary> checkIsWriterFriend(Long diaryId, Long userId);
    String  findRecentDiary(Long userId);
}
