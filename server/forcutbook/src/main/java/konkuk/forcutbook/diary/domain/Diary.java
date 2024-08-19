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
    private String title;
    private String content;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryImage> diaryImages = new ArrayList<>();

    public Diary(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Diary createDiary(User user, String title, String content, List<String> imageUrls){
        Diary diary = new Diary(title, content);
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
