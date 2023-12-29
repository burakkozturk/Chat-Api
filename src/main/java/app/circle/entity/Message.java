//package app.circle.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "message")
//public class Message {
//
//    @Id
//    @GeneratedValue
//    private UUID id;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "conversation_id")
////    private Conversation conversation;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sender_id")
//    private User sender;
//
//    private String content;
//
//    private LocalDateTime sentAt = LocalDateTime.now();
//}