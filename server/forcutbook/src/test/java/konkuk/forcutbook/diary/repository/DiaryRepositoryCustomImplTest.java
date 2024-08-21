package konkuk.forcutbook.diary.repository;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;
import konkuk.forcutbook.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class DiaryRepositoryCustomImplTest {

    @Autowired
    EntityManager em;
    @Autowired
    DiaryRepository diaryRepository;

    @Test
    void findDiaryListDtoByWriterId() {
        //given
        User user = createUser("user1");
        em.persist(user);

        Diary diary = Diary.createDiary(user, "test title", "test content", List.of("http://www.testImageUrl1", "http://www.testImageUrl2", "http://www.testImageUrl3"));
        em.persist(diary);

        em.flush();
        em.clear();

        //when
        List<DiaryListEachResDto> result = diaryRepository.findDiaryListDtoByWriterId(user.getId());

        //then
        DiaryListEachResDto findDiary = result.get(0);
        assertThat(findDiary.getDiaryId()).isEqualTo(diary.getId());
        assertThat(findDiary.getTitle()).isEqualTo(diary.getTitle());
        assertThat(findDiary.getContent()).isEqualTo(diary.getContent());
        assertThat(findDiary.getImageUrl()).isEqualTo("http://www.testImageUrl1");
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }
}