package app.circle.repository;

import app.circle.entity.Friend;
import app.circle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend,Long> {



    List<Friend> findByUser1OrUser2(UUID user1, UUID user2);

    @Query("SELECT f FROM Friend f WHERE (f.user1 = ?1 AND f.user2 = ?2) OR (f.user1 = ?2 AND f.user2 = ?1)")
    List<Friend> findFriendshipsBetweenUsers(UUID user1Id, UUID user2Id);

}
