package konkuk.forcutbook.diary.repository;

import konkuk.forcutbook.diary.domain.Diary;
import konkuk.global.domain.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom{
    Optional<Diary> findByIdAndWriterIdAndStatus(Long id, Long writerId, Status status);

    @EntityGraph(attributePaths = "diaryImages")
    Optional<Diary> findByIdAndStatus(Long id, Status status);

    Long countByWriterIdAndStatus(Long writerId, Status status);
}
