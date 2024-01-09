package app.circle.service;

import app.circle.dto.FriendRequestDto;
import app.circle.entity.Friendship;
import app.circle.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FriendshipService {

    private final FriendshipRepository friendShipRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendShipRepository) {
        this.friendShipRepository = friendShipRepository;
    }

    // Get sent friendship requests for a user
    public List<Friendship> getSentFriendshipRequests(UUID userId) {
        return friendShipRepository.findByUser1IdAndStatus(userId, 1);
    }


    // Get all friendship for a user
    public List<Friendship> getAllFriends(UUID userId) {
        return friendShipRepository.findByUser1IdAndStatus(userId, 3);
    }



    // Send friendship request
    public void sendFriendshipRequest(UUID senderId, UUID receiverId) {
        Friendship friendShip = new Friendship();
        friendShip.setUser1Id(senderId);
        friendShip.setUser2Id(receiverId);
        friendShip.setStatus(1); // Request has been sent
        friendShipRepository.save(friendShip);
    }

    // Accept friendship request
    public void acceptFriendshipRequest(Long friendshipId) {
        Friendship friendShip = friendShipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (friendShip.getStatus() == 1) {
            friendShip.setStatus(3); // Request has been accepted
            friendShipRepository.save(friendShip);
        } else {
            throw new RuntimeException("Invalid operation: Cannot accept a request that is not pending.");
        }
    }


    // Reject friendship request
    public void rejectFriendshipRequest(Long friendshipId) {
        Friendship friendShip = friendShipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (friendShip.getStatus() == 1) {
            friendShipRepository.deleteById(friendshipId);
        } else {
            throw new RuntimeException("Invalid operation: Cannot reject an accepted request.");
        }
    }




}
