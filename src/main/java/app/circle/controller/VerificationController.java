package app.circle.controller;

import app.circle.service.EmailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String toEmail) {
        try {
            emailService.sendVerificationEmail(toEmail);
            return ResponseEntity.ok("Verification email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending verification email: " + e.getMessage());
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyVerificationCode(@RequestParam String toEmail, @RequestParam String code) {
        if (emailService.verifyVerificationCode(toEmail, code)) {
            return ResponseEntity.ok("Verification code is valid!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Verification code is invalid.");
        }
    }
}
