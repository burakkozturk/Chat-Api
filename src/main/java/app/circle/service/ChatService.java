package app.circle.service;

import app.circle.entity.ChatHistory;
import app.circle.entity.Conversation;
import app.circle.entity.Message;
import app.circle.entity.User;
import app.circle.repository.ChatHistoryRepository;
import app.circle.repository.ConversationRepository;
import app.circle.repository.MessageRepository;
import app.circle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatService {

    private final MessageRepository messageRepository;

    private final ConversationRepository conversationRepository;

    private final ChatHistoryRepository chatHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatService(MessageRepository messageRepository, ConversationRepository conversationRepository, ChatHistoryRepository chatHistoryRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.chatHistoryRepository = chatHistoryRepository;
        this.userRepository = userRepository;
    }

    public void sendMessage(UUID senderId, Long conversationId, String messageContent) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    return conversationRepository.save(newConversation);
                });

        ChatHistory chatHistory = chatHistoryRepository.findByConversationId(conversation.getId())
                .orElseGet(() -> {
                    ChatHistory newChatHistory = new ChatHistory();
                    newChatHistory.setConversationId(conversation.getId());
                    return chatHistoryRepository.save(newChatHistory);

                });
        Message newMessage = new Message();
        newMessage.setContent(messageContent);
        newMessage.setSender(sender);
        newMessage.setChatHistory(chatHistory);
        messageRepository.save(newMessage);

        chatHistory.getMessages().add(newMessage);
        chatHistoryRepository.save(chatHistory);
    }
}