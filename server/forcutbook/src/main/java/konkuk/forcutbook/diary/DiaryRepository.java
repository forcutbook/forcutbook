package konkuk.forcutbook.diary;

import konkuk.forcutbook.diary.domain.Diary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByWriterId(Long userId);

    @EntityGraph(attributePaths = "diaryImages")
    Optional<Diary> findById(Long id);
}
