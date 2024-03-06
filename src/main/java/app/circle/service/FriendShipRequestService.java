package app.circle.service;

import app.circle.dto.FriendRequestDto;
import app.circle.dto.FriendSendedRequestDto;
import app.circle.entity.*;
import app.circle.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendShipRequestService {

    private final FriendshipRequestRepository friendshipRequestRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    public FriendShipRequestService(FriendshipRequestRepository friendshipRequestRepository, FriendRepository friendRepository, UserRepository userRepository, ConversationRepository conversationRepository, ChatHistoryRepository chatHistoryRepository) {
        this.friendshipRequestRepository = friendshipRequestRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.chatHistoryRepository = chatHistoryRepository;
    }


    public ResponseEntity<List<User>> getFriendsByUserId(UUID userId) {
        try {
            // Kullanıcının user1 veya user2 olduğu tüm arkadaşlıkları getir
            List<Friend> friendList = friendRepository.findByUser1OrUser2(userId, userId);

            // Friend entity'sinden User entity'sine dönüşüm
            List<UUID> friendIds = friendList.stream()
                    .map(friend -> friend.getUser1().equals(userId) ? friend.getUser2() : friend.getUser1())
                    .collect(Collectors.toList());

            // Bu friendId'leri kullanarak ilgili kullanıcıları getir
            List<User> friends = userRepository.findAllById(friendIds);

            return ResponseEntity.status(HttpStatus.OK).body(friends);
        } catch (Exception e) {
            // Hata durumunda hata cevabı döndür
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    public ResponseEntity<List<FriendRequestDto>> getAllRequests(UUID receiverId) {
        List<FriendshipRequest> friendshipRequests = friendshipRequestRepository.findByReceiverId(receiverId);

        if (friendshipRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Or you can customize the response body accordingly
        }

        List<FriendRequestDto> friendRequestDtos = friendshipRequests.stream()
                .map(FriendRequestDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(friendRequestDtos);
    }


    public ResponseEntity<List<FriendSendedRequestDto>> getAllSendedRequests(UUID senderId) {
        List<FriendshipRequest> friendshipRequests = friendshipRequestRepository.findBySenderId(senderId);

        if (friendshipRequests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Or you can customize the response body accordingly
        }

        List<FriendSendedRequestDto> friendRequestDtos = friendshipRequests.stream()
                .map(FriendSendedRequestDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(friendRequestDtos);
    }



    public ResponseEntity<String> sendFriendshipRequest(UUID senderId, UUID receiverId) {
        Optional<FriendshipRequest> existingRequest = friendshipRequestRepository
                .findBySenderIdAndReceiverId(senderId, receiverId);

        if (existingRequest.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Friendship request already sent.");
        }

        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setSenderId(senderId);
        friendshipRequest.setReceiverId(receiverId);

        // Save the friendship request
        friendshipRequestRepository.save(friendshipRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Friendship request sent successfully.");
    }


    public ResponseEntity<String> acceptFriendshipRequest(Long requestId) {
        Optional<FriendshipRequest> friendshipRequestOptional = friendshipRequestRepository.findById(requestId);

        if (friendshipRequestOptional.isPresent()) {
            FriendshipRequest friendshipRequest = friendshipRequestOptional.get();

            Friend friend = new Friend();
            friend.setUser1(friendshipRequest.getSenderId());
            friend.setUser2(friendshipRequest.getReceiverId());
            friendRepository.save(friend);

            // Yeni Conversation oluştur
            Conversation conversation = new Conversation();
            Set<User> members = new HashSet<>();
            userRepository.findById(friendshipRequest.getSenderId()).ifPresent(members::add);
            userRepository.findById(friendshipRequest.getReceiverId()).ifPresent(members::add);

            conversation.setMemberList(members);
            conversation.setConversationType(ConversationType.PRIVATE);

            // Conversation'ı kaydet
            Conversation savedConversation = conversationRepository.save(conversation);

            // Yeni ChatHistory oluştur
            ChatHistory chatHistory = new ChatHistory();
            chatHistory.setConversationId(savedConversation.getId()); // Kaydedilen Conversation'ın ID'si
            chatHistory.setMessages(new ArrayList<>()); // Boş mesaj listesi ile başlat

            // ChatHistory'yi kaydet
            chatHistoryRepository.save(chatHistory);

            // FriendshipRequest'i sil
            friendshipRequestRepository.deleteById(requestId);

            System.out.println("accepted"); // Konsola "accepted" yazdır

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Friendship request accepted and chat history created.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Friendship request not found.");
    }


    public ResponseEntity<String> removeRequest(Long requestId){
        friendshipRequestRepository.deleteById(requestId);
        return ResponseEntity.ok("Request deleted");
    }


    public ResponseEntity<String> removeFriends(Long friendId){
        Optional<Friend> friendOptional = friendRepository.findById(friendId);
        if (!friendOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Friend not found.");
        }

        Friend friend = friendOptional.get();
        UUID user1Id = friend.getUser1();
        UUID user2Id = friend.getUser2();

        // İlişkili Conversation'ları bul
        List<Conversation> conversations = conversationRepository.findByBothUserIds(user1Id, user2Id);
        for (Conversation conversation : conversations) {
            // İlişkili ChatHistory'yi bul ve sil
            Optional<ChatHistory> chatHistoryOptional = chatHistoryRepository.findByConversationId(conversation.getId());
            chatHistoryOptional.ifPresent(chatHistory -> chatHistoryRepository.delete(chatHistory));

            // Conversation'ı sil
            conversationRepository.delete(conversation);
        }

        // Arkadaşlık kaydını sil
        friendRepository.deleteById(friendId);

        return ResponseEntity.ok("Friend and related conversation and chat history removed.");
    }




}
