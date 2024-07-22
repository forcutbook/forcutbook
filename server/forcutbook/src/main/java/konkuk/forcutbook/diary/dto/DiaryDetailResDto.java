package konkuk.forcutbook.diary.dto;

import konkuk.forcutbook.diary.domain.Diary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PROTECTED)
@Getter
public class DiaryDetailResDto {
    private Long diaryId;
    private String title;
    private String content;
    //TODO 태그 친구 목록
    private List<DiaryImageResDto> images;
    private LocalDateTime date;
    private LocalDateTime createdAt;

    //TODO 친구 이미지 등등 추가
    public static DiaryDetailResDto toDto(Diary diary) {
        return DiaryDetailResDto.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .images(diary.getDiaryImages().stream()
                        .map(DiaryImageResDto::toDto).toList())
                .date(diary.getDate())
                .createdAt(diary.getCreatedAt())
                .build();
    }
}
