package konkuk.forcutbook.diary;

import konkuk.forcutbook.diary.dto.*;
import konkuk.forcutbook.global.response.BaseResponse;
import konkuk.global.dto.SuccessResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequestMapping("/users/{userId}/diaries")
@RequiredArgsConstructor
@Controller
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<BaseResponse> addDiary(@PathVariable Long userId,
                                                  @ModelAttribute DiaryAddDto diaryAddDto){
        Long id = diaryService.addDiary(userId, diaryAddDto);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/aiCreate")
    public ResponseEntity<BaseResponse> createAiDiary(@PathVariable Long userId,
                                                  @ModelAttribute DiaryAddDto diaryAddDto){
        AiDiaryResDto resultDto = diaryService.createAiDiary(userId, diaryAddDto);

        BaseResponse<AiDiaryResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<BaseResponse> updateDiary(@PathVariable Long userId,
                                                     @PathVariable Long diaryId,
                                                     @RequestBody DiaryUpdateDto diaryUpdateDto){
        Long id = diaryService.updateDiary(userId, diaryId, diaryUpdateDto);

        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<BaseResponse> deleteDiary(@PathVariable Long userId,
                                                     @PathVariable Long diaryId){
        Long id = diaryService.deleteDiary(userId, diaryId);


        BaseResponse<Object> response = new BaseResponse<>(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getDiaryList(@PathVariable Long userId){
        DiaryListResDto resultDto = diaryService.getDiaryList(userId);

        BaseResponse<DiaryListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<BaseResponse> getDiary(@PathVariable Long userId,
                                                      @PathVariable Long diaryId){
        DiaryDetailResDto resultDto = diaryService.getDiary(userId, diaryId);

        BaseResponse<DiaryDetailResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/friends/{friendId}")
    public ResponseEntity<BaseResponse> getFriendDiaryList(@PathVariable Long userId,
                                                              @PathVariable Long friendId){
        DiaryListResDto resultDto = diaryService.getFriendDiaryList(userId, friendId);

        BaseResponse<DiaryListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<BaseResponse> getDiaryFeed(@PathVariable Long userId){
        DiaryFeedListResDto resultDto = diaryService.getDiaryFeed(userId);

        BaseResponse<DiaryFeedListResDto> response = new BaseResponse<>(resultDto);
        return ResponseEntity.ok(response);
    }
}
