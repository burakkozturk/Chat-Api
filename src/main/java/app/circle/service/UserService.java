package app.circle.service;
import app.circle.entity.User;
import app.circle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        List<User> adminUsers = new ArrayList<>();

        // ROLE_ADMIN'e sahip kullanıcıları sorgulayın
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (isAdmin(user.getAuthorities())) {
                adminUsers.add(user);
            }
        }

        return adminUsers;
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

}
