package konkuk.forcutbook.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserDiary extends TimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userDiaryId")
    private Long id;

    //TODO User 연관 관계 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diaryId")
    private Diary diary;
}
