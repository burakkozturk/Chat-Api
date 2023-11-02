package app.circle.controller;

import app.circle.entity.User;
import app.circle.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllAdminUsers() {
        List<User> adminUsers = userService.getAllUsers();
        return adminUsers;
    }
}
