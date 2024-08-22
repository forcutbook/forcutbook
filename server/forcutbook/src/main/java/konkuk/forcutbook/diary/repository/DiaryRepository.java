package konkuk.forcutbook.diary.repository;

import konkuk.forcutbook.diary.domain.Diary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom{
    Optional<Diary> findByIdAndWriterId(Long id, Long writerId);

    @EntityGraph(attributePaths = "diaryImages")
    Optional<Diary> findById(Long id);

    Long countByWriterId(Long writerId);
}
