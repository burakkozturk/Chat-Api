package app.circle.dto;

import app.circle.entity.FriendshipRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private Long id;
    private UUID senderId;


    public FriendRequestDto(FriendshipRequest friendshipRequest) {
        if (friendshipRequest != null) {
            this.id = friendshipRequest.getId();
            this.senderId = friendshipRequest.getSenderId();
        }
    }
}
