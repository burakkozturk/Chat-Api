package app.circle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "_block")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID blockerUserId;

    private UUID blockedUserId;

}
