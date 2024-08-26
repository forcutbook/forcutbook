package konkuk.forcutbook.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiaryListResDto {
    private Long userId;
    private List<DiaryListEachResDto> diaries;
}
