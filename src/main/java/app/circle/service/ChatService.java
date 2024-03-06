package app.circle.service;

import app.circle.entity.ChatHistory;
import app.circle.entity.Message;
import app.circle.entity.User;
import app.circle.repository.ChatHistoryRepository;
import app.circle.repository.MessageRepository;
import app.circle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatService {


    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final ChatHistoryRepository chatHistoryRepository;

    public ChatService(MessageRepository messageRepository, UserRepository userRepository, ChatHistoryRepository chatHistoryRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatHistoryRepository = chatHistoryRepository;
    }

    public Message sendMessage(UUID senderId, Long chatHistoryId, String content) {

        if (senderId == null || chatHistoryId == null) {
            throw new IllegalArgumentException("senderId and chatHistoryId must not be null");
        }

        // Kullanıcı ve ChatHistory nesnelerini bul
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("User not found"));
        ChatHistory chatHistory = chatHistoryRepository.findById(chatHistoryId).orElseThrow(() -> new RuntimeException("Chat history not found"));

        // Yeni mesaj oluştur
        Message message = new Message();
        message.setContent(content);
        message.setSenderId(senderId);
        message.setChatHistory(chatHistory);
        message.setSendDateTime(LocalDateTime.now());

        // Mesajı kaydet ve dön
        return messageRepository.save(message);
    }

}