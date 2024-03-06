package app.circle.controller;

import app.circle.dto.SendMessageRequest;
import app.circle.entity.Message;
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

    @PostMapping("/send-message")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest request) {
        Message sentMessage = chatService.sendMessage(request.getSenderId(), request.getChatHistoryId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }
}
