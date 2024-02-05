package app.circle.repository;

import app.circle.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {

    @Query("SELECT c FROM Conversation c WHERE :user1Id IN (SELECT m.id FROM c.memberList m) AND :user2Id IN (SELECT m.id FROM c.memberList m)")
    List<Conversation> findByBothUserIds(@Param("user1Id") UUID user1Id, @Param("user2Id") UUID user2Id);
}