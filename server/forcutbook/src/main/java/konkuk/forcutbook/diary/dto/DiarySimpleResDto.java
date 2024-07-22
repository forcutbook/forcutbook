package konkuk.forcutbook.diary.dto;

import konkuk.forcutbook.diary.domain.Diary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PROTECTED)
@Getter
public class DiarySimpleResDto {
    private Long diaryId;
    private String title;
    private String content;
    private Boolean isImage;
    private LocalDateTime date;
    private LocalDateTime createdAt;

    public static DiarySimpleResDto toDto(Diary diary){
        return DiarySimpleResDto.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .isImage(!diary.getDiaryImages().isEmpty())
                .date(diary.getDate())
                .createdAt(diary.getCreatedAt())
                .build();
    }
}
