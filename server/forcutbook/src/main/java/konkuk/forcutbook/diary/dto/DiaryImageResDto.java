package konkuk.forcutbook.diary.dto;

import konkuk.forcutbook.diary.domain.DiaryImage;
import lombok.Getter;

@Getter
public class DiaryImageResDto {
    private Long imageId;
    private String imageUrl;

    public DiaryImageResDto(Long imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public static DiaryImageResDto toDto(DiaryImage diaryImage){
        return new DiaryImageResDto(diaryImage.getId(), diaryImage.getImageUrl());
    }
}
