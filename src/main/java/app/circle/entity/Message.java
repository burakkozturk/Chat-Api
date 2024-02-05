package app.circle.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private User sender;



    @ManyToOne
    @JoinColumn(name = "chat_history_id")
    private ChatHistory chatHistory;

}
