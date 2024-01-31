package app.circle.repository;

import app.circle.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory,Long> {
    Optional<ChatHistory> findByConversationId(Long id);
}
