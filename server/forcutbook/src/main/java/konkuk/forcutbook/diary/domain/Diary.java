package konkuk.forcutbook.diary.domain;

import jakarta.persistence.*;
import konkuk.forcutbook.domain.user.User;
import konkuk.global.domain.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Diary extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diaryId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User writer;

    //TODO List<User> 태그된 친구 연관관계 매핑

    private String title;
    private String content;
    private LocalDateTime date;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryImage> diaryImages = new ArrayList<>();

    public Diary(String title, String content, LocalDateTime date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public static Diary createDiary(User user, String title, String content, List<String> imageUrls, LocalDateTime date){
        Diary diary = new Diary(title, content, date);
        diary.writer = user;
        diary.addDiaryImage(imageUrls);
        return diary;
    }

    public void addDiaryImage(String imageUrl){
        DiaryImage diaryImage = new DiaryImage(this, imageUrl);
        diaryImages.add(diaryImage);
    }

    public void addDiaryImage(List<String> imageUrls){
        imageUrls.forEach(this::addDiaryImage);
    }
}
