package app.circle.repository;

import app.circle.entity.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {

    Optional<FriendshipRequest> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);

    List<FriendshipRequest> findByReceiverId(UUID receiverId);

    List<FriendshipRequest> findBySenderId(UUID senderId);

}
