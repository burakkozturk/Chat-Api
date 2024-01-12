package app.circle.repository;

import app.circle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE LOWER(u.nickname) LIKE LOWER(CONCAT('%', :nickname, '%'))")
    List<User> findByNicknameContainingIgnoreCase(@Param("nickname") String nickname);

}
