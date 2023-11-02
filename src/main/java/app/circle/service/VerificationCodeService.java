package app.circle.service;
import app.circle.entity.VerificationCode;
import app.circle.utils.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationCodeService {


    @Autowired
    private JavaMailSender mailSender;

    private Map<String, VerificationCode> verificationCodes = new HashMap<>();

    public void sendVerificationEmail(String toEmail) {
        String code = VerificationCodeGenerator.generateVerificationCode();
        LocalDateTime expirationTime = LocalDateTime.now().plus(2, ChronoUnit.MINUTES);
        VerificationCode verificationCode = new VerificationCode(code, expirationTime);
        verificationCodes.put(toEmail, verificationCode);

        String subject = "Doğrulama Kodunuz";
        String body = "Doğrulama kodunuz: " + code;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("oztburak240@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Doğrulama e-postası gönderildi.");
    }

    public boolean verifyVerificationCode(String email, String code) {
        VerificationCode verificationCode = verificationCodes.get(email);
        if (verificationCode == null) {
            return false; // Geçerli bir doğrulama kodu bulunamadı.
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(verificationCode.getExpirationTime()) && code.equals(verificationCode.getCode())) {
            return true; // Doğrulama kodu geçerli ve hala süresi içinde.
        } else {
            verificationCodes.remove(email); // Süresi geçen kodu kaldır.
            return false;
        }
    }
}