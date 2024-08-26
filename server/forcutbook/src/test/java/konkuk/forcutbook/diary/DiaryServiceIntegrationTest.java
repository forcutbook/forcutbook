package konkuk.forcutbook.diary;

import jakarta.persistence.EntityManager;
import konkuk.forcutbook.diary.domain.Diary;
import konkuk.forcutbook.diary.dto.*;
import konkuk.forcutbook.diary.exception.DiaryException;
import konkuk.forcutbook.diary.repository.DiaryRepository;
import konkuk.forcutbook.domain.user.User;
import konkuk.forcutbook.friend.domain.Friend;
import konkuk.forcutbook.global.domain.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
class DiaryServiceIntegrationTest {

    @Autowired
    EntityManager em;
    @Autowired
    DiaryService diaryService;
    @Autowired
    DiaryRepository diaryRepository;

    @Test
    @DisplayName("다이어리 생성 - 이미지 없음")
    void addDiary() {
        //given
        User user1 = createUser("user1");
        em.persist(user1);

        //when
        DiaryAddDto diaryAddDto = new DiaryAddDto("title1", "content1", null);
        Long diaryId = diaryService.addDiary(user1.getId(), diaryAddDto);

        em.flush();
        em.clear();

        //then
        Diary findDiary = diaryRepository.findById(diaryId).orElse(null);
        assertThat(findDiary).isNotNull();
        assertThat(findDiary.getTitle()).isEqualTo(diaryAddDto.getTitle());
        assertThat(findDiary.getContent()).isEqualTo(diaryAddDto.getContent());
    }

    @Test
    @DisplayName("다이어리 수정 - 권한o")
    void updateDiary() {
        //given
        User user1 = createUser("user1");
        em.persist(user1);

        Diary diary1 = Diary.createDiary(user1, "title1", "content1", List.of("imageUrl1", "imageUrl2", "imageUrl3"));
        em.persist(diary1);

        em.flush();
        em.clear();

        //when
        DiaryUpdateDto diaryUpdateDto = new DiaryUpdateDto("updated title", "updated content");
        diaryService.updateDiary(user1.getId(), diary1.getId(), diaryUpdateDto);

        em.flush();
        em.clear();

        //then
        Diary findDiary = diaryRepository.findById(diary1.getId()).orElse(null);
        assertThat(findDiary).isNotNull();
        assertThat(findDiary.getTitle()).isEqualTo(diaryUpdateDto.getTitle());
        assertThat(findDiary.getContent()).isEqualTo(diaryUpdateDto.getContent());
    }

    @Test
    @DisplayName("다이어리 삭제 - 권한o")
    void deleteDiary() {
        //given
        User user1 = createUser("user1");
        em.persist(user1);

        Diary diary1 = Diary.createDiary(user1, "title1", "content1", List.of("imageUrl1", "imageUrl2", "imageUrl3"));
        em.persist(diary1);

        em.flush();
        em.clear();

        //when
        diaryService.deleteDiary(user1.getId(), diary1.getId());

        //then
        Diary findDiary = diaryRepository.findById(diary1.getId()).orElse(null);
        assertThat(findDiary).isNotNull();
        assertThat(findDiary.getTitle()).isEqualTo(diary1.getTitle());
        assertThat(findDiary.getContent()).isEqualTo(diary1.getContent());
        assertThat(findDiary.getStatus()).isEqualTo(Status.DELETED);
    }

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

        List<DiaryListEachResDto> diaries = results.getDiaries();
        assertThat(diaries.stream().map(DiaryListEachResDto::getDiaryId)).contains(diary1.getId(), diary2.getId());
        assertThat(diaries.stream().map(DiaryListEachResDto::getTitle)).contains(diary1.getTitle(), diary2.getTitle());
        assertThat(diaries.stream().map(DiaryListEachResDto::getContent)).contains(diary1.getContent(), diary2.getContent());
        assertThat(diaries.stream().map(DiaryListEachResDto::getImageUrl)).contains("imageUrl1");
    }

    @Test
    @DisplayName("친구의 다이어리 조회 - 팔로워일 때")
    void getFriendDiaryList() {
        //given
        User user = createUser("user");
        User friend = createUser("friendShip");
        em.persist(user);
        em.persist(friend);

        Friend friendShip = Friend.createFriend(user, friend);
        friendShip.setAccept(true);
        em.persist(friendShip);

        Diary diary1 = Diary.createDiary(friend, "title1", "content1", List.of("imageUrl1", "imageUrl2", "imageUrl3"));
        Diary diary2 = Diary.createDiary(friend, "title2", "content2", List.of());
        Diary otherDiary = Diary.createDiary(user, "other", "other", List.of());
        em.persist(diary1);
        em.persist(diary2);
        em.persist(otherDiary);

        em.flush();
        em.clear();

        //when
        DiaryListResDto results = diaryService.getFriendDiaryList(user.getId(), friend.getId());

        //then
        assertThat(results.getUserId()).isEqualTo(friend.getId());

        List<DiaryListEachResDto> diaries = results.getDiaries();
        assertThat(diaries.stream().map(DiaryListEachResDto::getDiaryId)).contains(diary1.getId(), diary2.getId());
        assertThat(diaries.stream().map(DiaryListEachResDto::getTitle)).contains(diary1.getTitle(), diary2.getTitle());
        assertThat(diaries.stream().map(DiaryListEachResDto::getContent)).contains(diary1.getContent(), diary2.getContent());
        assertThat(diaries.stream().map(DiaryListEachResDto::getImageUrl)).contains("imageUrl1");
    }

    @Test
    @DisplayName("다이어리 피드 조회")
    void test() {
        //given
        User user = createUser("user");
        User friend1 = createUser("friend1");
        User friend2 = createUser("friend2");
        em.persist(user);
        em.persist(friend1);
        em.persist(friend2);

        Friend friendShip1 = Friend.createFriend(user, friend1);
        friendShip1.setAccept(true);
        em.persist(friendShip1);
        Friend friendShip2 = Friend.createFriend(user, friend2);
        friendShip2.setAccept(true);
        em.persist(friendShip2);

        Diary diary1 = Diary.createDiary(user, "title1", "content1", List.of("imageUrl1", "imageUrl2", "imageUrl3"));
        Diary diary2 = Diary.createDiary(friend1, "title2", "content2", List.of());
        Diary diary3 = Diary.createDiary(friend2, "title3", "content3", List.of());
        em.persist(diary1);
        em.persist(diary2);
        em.persist(diary3);

        em.flush();
        em.clear();

        //when
        DiaryFeedListResDto results = diaryService.getDiaryFeed(user.getId());

        //then
        assertThat(results.getUserId()).isEqualTo(user.getId());

        List<DiaryFeedResDto> diaries = results.getDiaries();
        assertThat(diaries.stream().map(DiaryFeedResDto::getUserId)).containsExactly(friend2.getId(), friend1.getId(), user.getId());
        assertThat(diaries.stream().map(DiaryFeedResDto::getUserName)).containsExactly(friend2.getUserName(), friend1.getUserName(), user.getUserName());
        assertThat(diaries.stream().map(DiaryFeedResDto::getDiaryId)).contains(diary1.getId(), diary2.getId());
        assertThat(diaries.stream().map(DiaryFeedResDto::getTitle)).contains(diary1.getTitle(), diary2.getTitle());
        assertThat(diaries.stream().map(DiaryFeedResDto::getContent)).contains(diary1.getContent(), diary2.getContent());
        assertThat(diaries.stream().map(DiaryFeedResDto::getImageUrl)).contains("imageUrl1");
    }

    @Test
    @DisplayName("특정 다이어리 조회 - 작성자 조회")
    void getDiaryMine() {
        //given
        User user = createUser("me");
        em.persist(user);

        Diary diary = Diary.createDiary(user, "title!", "content", null);
        em.persist(diary);

        em.flush();
        em.clear();

        //when
        DiaryDetailResDto result = diaryService.getDiary(user.getId(), diary.getId());

        //then
        assertThat(result.getDiaryId()).isEqualTo(diary.getId());
        assertThat(result.getTitle()).isEqualTo(diary.getTitle());
        assertThat(result.getContent()).isEqualTo(diary.getContent());
    }

    @Test
    @DisplayName("특정 다이어리 조회 - 친구 관계 조회")
    void getDiaryFriend() {
        //given
        User user = createUser("me");
        em.persist(user);
        User friend = createUser("friend");
        em.persist(friend);

        Friend friendShip = Friend.createFriend(user, friend);
        friendShip.setAccept(true);
        em.persist(friendShip);

        Diary diary = Diary.createDiary(friend, "title!", "content", null);
        em.persist(diary);

        em.flush();
        em.clear();

        //when
        DiaryDetailResDto result = diaryService.getDiary(user.getId(), diary.getId());

        //then
        assertThat(result.getDiaryId()).isEqualTo(diary.getId());
        assertThat(result.getTitle()).isEqualTo(diary.getTitle());
        assertThat(result.getContent()).isEqualTo(diary.getContent());
    }

    @Test
    @DisplayName("특정 다이어리 조회 - 친구 관계 아님")
    void getDiaryNotFriend() {
        //given
        User user = createUser("me");
        em.persist(user);
        User friend = createUser("friend");
        em.persist(friend);

        Friend friendShip = Friend.createFriend(user, friend);
        friendShip.setAccept(false);
        em.persist(friendShip);

        Diary diary = Diary.createDiary(friend, "title!", "content", null);
        em.persist(diary);

        em.flush();
        em.clear();

        //when
        assertThatThrownBy(() -> diaryService.getDiary(user.getId(), diary.getId())).isInstanceOf(DiaryException.class);
    }

    User createUser(String username){
        User user = new User();
        user.setUserName(username);
        user.setPassword("test");
        return user;
    }
}