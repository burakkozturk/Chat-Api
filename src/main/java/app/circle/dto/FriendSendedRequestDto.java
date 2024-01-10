package app.circle.dto;

import app.circle.entity.FriendshipRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendSendedRequestDto {
    private Long id;
    private UUID receiverId;


    public FriendSendedRequestDto(FriendshipRequest friendshipRequest) {
        if (friendshipRequest != null) {
            this.id = friendshipRequest.getId();
            this.receiverId = friendshipRequest.getReceiverId();
        }
    }
}
