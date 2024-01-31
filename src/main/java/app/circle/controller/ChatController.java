package app.circle.controller;

import app.circle.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam UUID senderId,
                                         @RequestParam Long conversationId,
                                         @RequestBody String messageContent) {
        try {
            chatService.sendMessage(senderId, conversationId, messageContent);
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending message: " + e.getMessage());
        }
    }
}
