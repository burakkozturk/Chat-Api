package app.circle.controller;

import app.circle.entity.Friendship;
import app.circle.service.FriendshipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friend-ship")
public class FriendshipController {

    private final FriendshipService friendShipService;

    public FriendshipController(FriendshipService friendShipService) {
        this.friendShipService = friendShipService;
    }


    @GetMapping("/friends/{userId}")
    public List<Friendship> getAllFriends(@PathVariable UUID userId) {
        return friendShipService.getAllFriends(userId);
    }


    @GetMapping("/requests/{userId}")
    public List<Friendship> getSentFriendshipRequests(@PathVariable UUID userId) {
        return friendShipService.getSentFriendshipRequests(userId);
    }


    @PostMapping("/send-request")
    public void sendFriendshipRequest(@RequestParam(name = "senderId", required = true) UUID senderId,
                                      @RequestParam(name = "receiverId", required = true) UUID receiverId) {
        friendShipService.sendFriendshipRequest(senderId, receiverId);
    }


    @PostMapping("/reject-request/{friendshipId}")
    public void rejectFriendshipRequest(@PathVariable Long friendshipId) {
        friendShipService.rejectFriendshipRequest(friendshipId);
    }


    @PostMapping("/accept-request/{friendshipId}")
    public void acceptFriendshipRequest(@PathVariable Long friendshipId) {
        friendShipService.acceptFriendshipRequest(friendshipId);
    }
}
