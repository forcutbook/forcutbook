package konkuk.forcutbook.diary.dto;

import lombok.Getter;

@Getter
public class AiDiaryResDto {
    private String title;
    private String content;

    public AiDiaryResDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
