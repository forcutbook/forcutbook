package konkuk.forcutbook.diary.dto;

import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DiaryListResDto {
    private Long userId;
    private List<DiaryListEachResDto> diaries;
}
