package konkuk.forcutbook.domain.user;

import konkuk.forcutbook.domain.user.dto.GetUserListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndPassword(String username, String password);

    @Query("SELECT u FROM User u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<User> getUserByUserName(@Param("username") String username);
}
