package app.circle.dto;

import java.util.UUID;

public class UpdateNicknameRequest {
    private UUID userId;
    private String newNickname;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public void setNewNickname(String newNickname) {
        this.newNickname = newNickname;
    }
}
