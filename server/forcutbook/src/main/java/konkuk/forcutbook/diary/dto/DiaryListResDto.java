package konkuk.forcutbook.diary.dto;

import konkuk.forcutbook.diary.domain.Diary;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DiaryListResDto {
    private Long userId;
    private String search;
    private List<DiarySimpleResDto> diaries = new ArrayList<>();

    private DiaryListResDto(Long userId, String search) {
        this.userId = userId;
        this.search = search;
    }

    public static DiaryListResDto toDto(Long userId, String search, List<Diary> diaries){
        DiaryListResDto resDto = new DiaryListResDto(userId, search);
        diaries.forEach((diary -> resDto.diaries.add(DiarySimpleResDto.toDto(diary))));
        return resDto;
    }
}
