package app.circle.dto;


import lombok.Data;

@Data
public class VerificationCodeRequestDto {
    private String toEmail;
    private String subject;
    private String body;

    // Getter ve Setter metodları...

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}