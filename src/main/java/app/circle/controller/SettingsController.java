package app.circle.controller;

import app.circle.dto.ProfileDto;
import app.circle.entity.User;
import app.circle.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PutMapping("/update-nickname/{userId}")
    public ResponseEntity<User> updateUserNickname(
            @PathVariable UUID userId,
            @RequestParam String newNickname
    ) {
        User updatedUser = settingsService.updateUserNickname(userId, newNickname);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-profile-photo/{userId}")
    public ResponseEntity<User> updateProfilePhoto(
            @PathVariable UUID userId,
            @RequestParam String newProfilePhoto
    ) {
        User updatedUser = settingsService.updateProfilePhoto(userId, newProfilePhoto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update-profile/{userId}")
    public ResponseEntity<User> updateProfile(@PathVariable UUID userId, @RequestBody ProfileDto profileDto){
        User updateUser = settingsService.updateUser(userId, profileDto);

        return ResponseEntity.ok(updateUser);
    }


    @GetMapping("/search")
    public ResponseEntity<List<User>> searchByNickname(@RequestParam String nickname) {
        List<User> users = settingsService.searchByNickname(nickname);
        return ResponseEntity.ok(users);
    }

}
