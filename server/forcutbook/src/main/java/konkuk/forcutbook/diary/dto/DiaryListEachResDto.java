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
    private Long userId;
    private String userName;
    private String userProfile;
    private Long diaryId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    @QueryProjection
    public DiaryListEachResDto(Long userId, String userName, String userProfile, Long diaryId, String title, String content, String imageUrl, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.userProfile = userProfile;
        this.diaryId = diaryId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}
