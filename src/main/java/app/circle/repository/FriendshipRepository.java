package app.circle.repository;

import app.circle.dto.FriendRequestDto;
import app.circle.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship,Long> {

    List<Friendship> findByUser1IdAndStatus(UUID user1Id, int status);


}
