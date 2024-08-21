package konkuk.forcutbook.diary;

import konkuk.forcutbook.diary.dto.*;
import konkuk.global.dto.SuccessResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/users/{userId}/diaries")
@RequiredArgsConstructor
@Controller
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<SuccessResDto> addDiary(@PathVariable Long userId,
                                                  @ModelAttribute DiaryAddDto diaryAddDto){
        Long diaryId = diaryService.addDiary(userId, diaryAddDto);
        return SuccessResDto.getResponseEntity(diaryId, SuccessResDto.SuccessMessage.ADD_SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/aiCreate")
    public ResponseEntity<AiDiaryResDto> createAiDiary(@PathVariable Long userId,
                                                  @ModelAttribute DiaryAddDto diaryAddDto){
        AiDiaryResDto responseDto = diaryService.createAiDiary(userId, diaryAddDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DiaryListResDto> getDiaryList(@PathVariable Long userId){
        DiaryListResDto dto = diaryService.getDiaryList(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetailResDto> getDiary(@PathVariable Long userId,
                                                      @PathVariable Long diaryId){
        DiaryDetailResDto responseDto = diaryService.getDiary(userId, diaryId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/friends/{friendId}")
    public ResponseEntity<DiaryListResDto> getFriendDiaryList(@PathVariable Long userId,
                                                              @PathVariable Long friendId){
        DiaryListResDto dto = diaryService.getFriendDiaryList(userId, friendId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<DiaryFeedListResDto> getDiaryFeed(@PathVariable Long userId){
        DiaryFeedListResDto dto = diaryService.getDiaryFeed(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
