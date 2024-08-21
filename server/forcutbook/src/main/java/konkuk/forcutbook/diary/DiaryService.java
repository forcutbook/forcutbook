package konkuk.forcutbook.diary;

import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.*;
import konkuk.forcutbook.diary.repository.DiaryRepository;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.domain.user.UserRepository;
import konkuk.forcutbook.friend.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final S3ServiceProvider s3ServiceProvider;

    @Value("${aws.s3.imageUrlPrefix}")
    private String imageUrlPrefix;

    @Transactional
    public Long addDiary(Long userId, DiaryAddDto diaryAddDto) {
        User user = findUser(userId);

        List<MultipartFile> imageFiles = diaryAddDto.getImages();
        List<String> imageUrls = (imageFiles == null) ? null : uploadImage(imageFiles);

        Diary diary = Diary.createDiary(user, diaryAddDto.getTitle(), diaryAddDto.getContent(), imageUrls);

        return diaryRepository.save(diary).getId();
    }

    private List<String> uploadImage(List<MultipartFile> imageFiles) {
        List<String> imageNames = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            String fileExtension = getFileExtension(imageFile.getOriginalFilename());
            String imageName = UUID.randomUUID().toString() + fileExtension;
            s3ServiceProvider.uploadImage(imageName, imageFile);
            imageNames.add(getImageUrl(imageName));
        }
        return imageNames;
    }

    public AiDiaryResDto createAiDiary(Long userId, DiaryAddDto diaryAddDto) {
        List<MultipartFile> imageFiles = diaryAddDto.getImages();

        return new AiDiaryResDto("AI title", "AI content");
    }

    private String getFileExtension(String originalFilename){
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    private String getImageUrl(String imageName){
        return imageUrlPrefix + imageName;
    }

    public DiaryListResDto getDiaryList(Long userId) {
        //검증 로직
        User user = findUser(userId);

        //서비스 로직
        List<DiaryListEachResDto> diaryDtoList = diaryRepository.findDiaryListDtoByWriterId(userId);
        Long diaryCount = diaryRepository.countByWriterId(userId);
        Long following = friendRepository.countBySenderIdAndIsAccept(userId, true);
        Long follower = friendRepository.countByReceiverIdAndIsAccept(userId, true);

        return DiaryListResDto.builder()
                .userId(userId)
                .username(user.getUserName())
                .follower(follower)
                .following(following)
                .diaryCount(diaryCount)
                .diaries(diaryDtoList)
                .build();
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
