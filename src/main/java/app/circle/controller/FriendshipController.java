package app.circle.controller;

import app.circle.entity.Friend;
import app.circle.entity.User;
import app.circle.repository.UserRepository;
import app.circle.service.FriendShipRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friend-ship")
public class FriendshipController {

    private final FriendShipRequestService  friendShipRequestService;

    public FriendshipController(FriendShipRequestService friendShipRequestService) {
        this.friendShipRequestService = friendShipRequestService;
    }



    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<User>> getFriendsByUserId(@PathVariable UUID userId) {
        ResponseEntity<List<User>> responseEntity = friendShipRequestService.getFriendsByUserId(userId);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequest(@RequestParam UUID receiverId){
        return friendShipRequestService.getAllRequests(receiverId);
    }

    @GetMapping("/sended-requests")
    public ResponseEntity<?> getAllSendedRequest(@RequestParam UUID senderId){
        return friendShipRequestService.getAllSendedRequests(senderId);
    }


    @PostMapping("/send-request")
    public ResponseEntity<String> sendFriendshipRequest(@RequestParam UUID senderId, @RequestParam UUID receiverId){
        friendShipRequestService.sendFriendshipRequest(senderId,receiverId);

        return ResponseEntity.ok("Request Sended");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendshipRequest(@RequestParam Long requestId) {
        return friendShipRequestService.acceptFriendshipRequest(requestId);

        /*

        Burada Accept Edildikten Sonra Conversation - Chat History Oluştursun.

        */
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId){
        return friendShipRequestService.removeRequest(requestId);
    }


    @DeleteMapping("/friends/{friendId}")
    public ResponseEntity<String> removeFromFriends(@PathVariable Long friendId){
        return friendShipRequestService.removeRequest(friendId);
    }






}
