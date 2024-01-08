package app.circle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "_chat_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "conversation_id")
    private Long conversationId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_history_id")
    private List<Message> messages;

}
