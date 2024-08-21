package konkuk.forcutbook.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryFeedResDto {
    private Long userId;
    private String userName;
//    private String userImage;
    private Long diaryId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    @QueryProjection
    public DiaryFeedResDto(Long userId, String userName, Long diaryId, String title, String content, String imageUrl, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
//        this.userImage = userImage;
        this.diaryId = diaryId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}
