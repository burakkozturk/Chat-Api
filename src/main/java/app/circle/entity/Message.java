package app.circle.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="_message")
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // will convert messageType


    @Column(columnDefinition = "BINARY(16)")
    private UUID senderId; // Mesajı gönderen kullanıcının UUID'si


    private LocalDateTime sendDateTime; // Mesaj gönderim tarihi

    @ManyToOne
    @JoinColumn(name = "chat_history_id")
    private ChatHistory chatHistory;

}
