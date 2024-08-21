package konkuk.forcutbook.diary.repository;

import konkuk.forcutbook.diary.dto.DiaryListEachResDto;

import java.util.List;

public interface DiaryRepositoryCustom {
    List<DiaryListEachResDto> findDiaryListDtoByWriterId(Long userId);
}
