package app.circle.repository;

import app.circle.entity.Friend;
import app.circle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend,Long> {



    List<Friend> findByUser1OrUser2(UUID user1, UUID user2);
}
