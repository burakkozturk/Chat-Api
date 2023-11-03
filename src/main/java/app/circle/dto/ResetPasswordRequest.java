package app.circle.dto;

import java.util.UUID;

public class ResetPasswordRequest {

    private UUID userId;
    private String newPassword;


    public ResetPasswordRequest(UUID userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
