package app.circle.dto;


public class LoginRequest {

    private String emailOrPhoneNumber; // E-posta veya telefon numarası

    private String password;

    public String getEmailOrPhoneNumber() {
        return emailOrPhoneNumber;
    }

    public void setEmailOrPhoneNumber(String emailOrPhoneNumber) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}