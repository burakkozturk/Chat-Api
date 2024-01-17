package app.circle.controller;

import app.circle.entity.BanList;
import app.circle.entity.User;
import app.circle.service.AdministrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdministrationController {

    private final AdministrationService administrationService;

    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> list = administrationService.getAllUsers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable UUID userId){
        Optional<User> user = administrationService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/banned")
    public ResponseEntity<List<BanList>> getAllBannedUsers(){
        List<BanList> list = administrationService.getAllBannedUsers();
        return ResponseEntity.ok(list);
    }


    @PostMapping("/ban/{userId}")
    public ResponseEntity<BanList> banUser(@PathVariable UUID userId) {
        BanList ban = administrationService.banUser(userId);
        return ResponseEntity.ok(ban);
    }


    @DeleteMapping("/unban/{banId}")
    public ResponseEntity<?> unbanUser(@PathVariable Long banId) {
        administrationService.unbanUser(banId);
        return ResponseEntity.ok().build();
    }
}
