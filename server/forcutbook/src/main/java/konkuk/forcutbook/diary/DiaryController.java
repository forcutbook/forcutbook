package konkuk.forcutbook.diary;

import konkuk.forcutbook.diary.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/users/{userId}/diaries")
    public ResponseEntity<SuccessResDto> addDiary(@PathVariable Long userId,
                                                  @ModelAttribute DiaryAddDto diaryAddDto){
        Long diaryId = diaryService.addDiary(userId, diaryAddDto);
        return SuccessResDto.getResponseEntity(diaryId, SuccessResDto.SuccessMessage.ADD_SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/diaries/aiCreate")
    public ResponseEntity<AiDiaryResDto> createAiDiary(@PathVariable Long userId,
                                                  @ModelAttribute DiaryAddDto diaryAddDto){
        AiDiaryResDto responseDto = diaryService.createAiDiary(userId, diaryAddDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/diaries")
    public ResponseEntity<DiaryListResDto> getDiaryList(@PathVariable Long userId,
                                                        @RequestParam(required = false) String search){
        DiaryListResDto responseDto = diaryService.getDiaryList(userId, search);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/diaries/{diaryId}")
    public ResponseEntity<DiaryDetailResDto> getDiary(@PathVariable Long userId,
                                                      @PathVariable Long diaryId){
        DiaryDetailResDto responseDto = diaryService.getDiary(userId, diaryId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
