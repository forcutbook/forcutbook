package konkuk.forcutbook.diary;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.DiaryListEachResDto;
import konkuk.forcutbook.diary.dto.DiaryListResDto;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.friend.domain.Friend;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class DiaryServiceIntegrationTest {

    @Autowired
    EntityManager em;
    @Autowired
    DiaryService diaryService;

    @DisplayName("내 다이어리 목록 조회")
    @Test
    void getDiaryList() {
        //given
        User user1 = createUser("user1");
        User user2 = createUser("user2");
        em.persist(user1);
        em.persist(user2);

        Friend friend = Friend.createFriend(user1, user2);
        friend.setAccept(true);
        em.persist(friend);

        Diary diary1 = Diary.createDiary(user1, "title1", "content1", List.of("imageUrl1", "imageUrl2", "imageUrl3"));
        Diary diary2 = Diary.createDiary(user1, "title2", "content2", List.of());
        Diary otherDiary = Diary.createDiary(user2, "other", "other", List.of());
        em.persist(diary1);
        em.persist(diary2);
        em.persist(otherDiary);

        em.flush();
        em.clear();

        //when
        DiaryListResDto results = diaryService.getDiaryList(user1.getId());

        //then
        assertThat(results.getUserId()).isEqualTo(user1.getId());
        assertThat(results.getUsername()).isEqualTo(user1.getUserName());
        assertThat(results.getDiaryCount()).isEqualTo(2);
        assertThat(results.getFollower()).isEqualTo(0);
        assertThat(results.getFollowing()).isEqualTo(1);

        List<DiaryListEachResDto> diaries = results.getDiaries();
        assertThat(diaries.stream().map(DiaryListEachResDto::getDiaryId)).contains(diary1.getId(), diary2.getId());
        assertThat(diaries.stream().map(DiaryListEachResDto::getTitle)).contains(diary1.getTitle(), diary2.getTitle());
        assertThat(diaries.stream().map(DiaryListEachResDto::getContent)).contains(diary1.getContent(), diary2.getContent());
        assertThat(diaries.stream().map(DiaryListEachResDto::getImageUrl)).contains("imageUrl1");
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword("test");
        return user;
    }
}