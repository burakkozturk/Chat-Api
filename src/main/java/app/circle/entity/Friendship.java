package app.circle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "_friend_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user1_id")
    private UUID user1Id;

    @Column(name = "user2_id")
    private UUID user2Id;

    private int status;

    /*

    * if status 1 request has been sent
    * if status 2 request is rejected
    * if status 3 request has been accepted

    */
}
