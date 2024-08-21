package konkuk.forcutbook.diary.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DiaryFeedListResDto {
    private Long userId;
    private List<DiaryFeedResDto> diaries;

    public DiaryFeedListResDto(Long userId, List<DiaryFeedResDto> diaries) {
        this.userId = userId;
        this.diaries = diaries;
    }
}
