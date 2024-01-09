package app.circle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private Long id;
    private UUID user2Id;
    private int status;
}
