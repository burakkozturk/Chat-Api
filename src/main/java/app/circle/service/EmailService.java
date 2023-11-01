package app.circle.service;
import app.circle.utils.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Geçici kod deposu olarak bir HashMap kullanıyoruz
    private Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationEmail(String toEmail) {
        String code = VerificationCodeGenerator.generateVerificationCode();
        verificationCodes.put(toEmail, code);  // E-posta adresini ve kodu sakla

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
        String storedCode = verificationCodes.get(email);
        return code.equals(storedCode);
    }
}