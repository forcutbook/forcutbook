package konkuk.forcutbook.diary;

import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.AiDiaryResDto;
import konkuk.forcutbook.diary.dto.DiaryAddDto;
import konkuk.forcutbook.diary.dto.DiaryDetailResDto;
import konkuk.forcutbook.diary.dto.DiaryListResDto;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final S3ServiceProvider s3ServiceProvider;

    @Value("${aws.s3.imageUrlPrefix}")
    private String imageUrlPrefix;

    @Transactional
    public Long addDiary(Long userId, DiaryAddDto diaryAddDto) {
        User user = findUser(userId);

        List<MultipartFile> imageFiles = diaryAddDto.getImages();
        List<String> imageUrls = uploadImage(imageFiles);

        Diary diary = Diary.createDiary(user, diaryAddDto.getTitle(), diaryAddDto.getContent(), imageUrls, diaryAddDto.getDate());

        return diaryRepository.save(diary).getId();
    }

    private List<String> uploadImage(List<MultipartFile> imageFiles) {
        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            String imageName = UUID.randomUUID().toString();
            s3ServiceProvider.uploadImage(imageName, imageFile);
            imageNames.add(getImageUrl(imageName));
        }
        return imageNames;
    }

    public AiDiaryResDto createAiDiary(Long userId, DiaryAddDto diaryAddDto) {
        List<MultipartFile> imageFiles = diaryAddDto.getImages();

        return new AiDiaryResDto("AI title", "AI content");
    }

    private String getImageUrl(String imageName){
        return imageUrlPrefix + imageName;
    }

    public DiaryListResDto getDiaryList(Long userId, String search) {
        List<Diary> diaryList = diaryRepository.findByWriterId(userId); //TODO search 조건 넣어야함
        return DiaryListResDto.toDto(userId, search, diaryList);
    }

    public DiaryDetailResDto getDiary(Long userId, Long diaryId){
        Diary diary = findDiaryWithDiaryImage(diaryId);
        return DiaryDetailResDto.toDto(diary);
    }

    private User findUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    private Diary findDiaryWithDiaryImage(Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow();
    }
}
