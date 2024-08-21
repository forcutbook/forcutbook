package konkuk.forcutbook.diary.dto;

import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class DiaryListResDto {
    private Long userId;
    private String username;
    private String search;
    private Long follower;
    private Long following;
    private Long diaryCount;
    private List<DiaryListEachResDto> diaries;
}
