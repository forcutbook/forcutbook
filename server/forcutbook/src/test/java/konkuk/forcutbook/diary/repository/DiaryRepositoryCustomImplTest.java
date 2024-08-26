package konkuk.forcutbook.diary.repository;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.DiaryFeedResDto;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;
import konkuk.forcutbook.user.User;
import konkuk.forcutbook.friend.domain.Friend;
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

    @Test
    void findDiaryListForFeed() {
        //given
        User user1 = createUser("user1");
        User user2 = createUser("user2");
        em.persist(user1);
        em.persist(user2);

        Friend friendShip = Friend.createFriend(user1, user2);
        friendShip.setAccept(true);
        em.persist(friendShip);

        Diary diary1 = Diary.createDiary(user1, "test title", "test content", List.of("http://www.testImageUrl1", "http://www.testImageUrl2", "http://www.testImageUrl3"));
        Diary diary2 = Diary.createDiary(user1, "test title", "test content", List.of("http://www.testImageUrl2", "http://www.testImageUrl1", "http://www.testImageUrl3"));
        Diary diary3 = Diary.createDiary(user2, "test title", "test content", List.of("http://www.testImageUrl3", "http://www.testImageUrl2", "http://www.testImageUrl1"));
        em.persist(diary1);
        em.persist(diary2);
        em.persist(diary3);

        em.flush();
        em.clear();

        //when
        List<DiaryFeedResDto> results = diaryRepository.findDiaryListForFeed(user1.getId());

        //then
        assertThat(results.stream().map(DiaryFeedResDto::getUserId)).containsExactly(user2.getId(), user1.getId(), user1.getId());
        assertThat(results.stream().map(DiaryFeedResDto::getUserName)).containsExactly(user2.getUserName(), user1.getUserName(), user1.getUserName());
        assertThat(results.stream().map(DiaryFeedResDto::getDiaryId)).containsExactly(diary3.getId(), diary2.getId(), diary1.getId());
        assertThat(results.stream().map(DiaryFeedResDto::getTitle)).containsExactly(diary3.getTitle(), diary2.getTitle(), diary1.getTitle());
        assertThat(results.stream().map(DiaryFeedResDto::getContent)).containsExactly(diary3.getContent(), diary2.getContent(), diary1.getContent());
        assertThat(results.stream().map(DiaryFeedResDto::getImageUrl)).containsExactly("http://www.testImageUrl3", "http://www.testImageUrl2", "http://www.testImageUrl1");
//        assertThat(results.stream().map(DiaryFeedResDto::getCreatedAt)).containsExactly(diary3.getCreatedAt(), diary2.getCreatedAt(), diary1.getCreatedAt());
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }
}