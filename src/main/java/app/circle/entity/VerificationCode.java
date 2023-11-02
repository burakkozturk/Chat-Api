package app.circle.entity;

import java.time.LocalDateTime;

public class VerificationCode {
    private String email;
    private String code;
    private LocalDateTime expirationTime;


    public VerificationCode(String code, LocalDateTime expirationTime) {
        this.code = code;
        this.expirationTime = expirationTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
