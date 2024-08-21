package konkuk.forcutbook.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import konkuk.forcutbook.diary.domain.Diary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PROTECTED)
@Getter
public class DiaryListEachResDto {
    private Long diaryId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    @QueryProjection
    public DiaryListEachResDto(Long diaryId, String title, String content, String imageUrl, LocalDateTime createdAt) {
        this.diaryId = diaryId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public static DiaryListEachResDto toDto(Diary diary){
//        String titleImgUrl = diary.getDiaryImages()
        return DiaryListEachResDto.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
//                .imageUrl()
                .createdAt(diary.getCreatedAt())
                .build();
    }
}
