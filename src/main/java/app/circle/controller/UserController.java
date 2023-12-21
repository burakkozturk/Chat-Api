package app.circle.controller;

import app.circle.dto.ResetPasswordRequest;
import app.circle.dto.UpdateNicknameRequest;
import app.circle.dto.UserUpdateRequest;
import app.circle.entity.User;
import app.circle.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @PutMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateNicknameRequest request) {


        UUID userId = request.getUserId();
        String newNickname = request.getNewNickname();
        User updatedUser = userService.updateNickname(userId, newNickname);

        return ResponseEntity.ok("Nickname updated: " + updatedUser.getNickname());
    }

    @PutMapping("/{userId}/reset-password")
    public ResponseEntity<String> updatePassword(
            @PathVariable UUID userId,
            @RequestBody String newPassword
    ) {
        try {
            userService.updatePassword(userId, newPassword);
            return ResponseEntity.ok("Password updated successfully .");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest userUpdateDTO) {
        User updatedUser = userService.updateUser(userId, userUpdateDTO);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
