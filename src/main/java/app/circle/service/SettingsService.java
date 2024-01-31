package app.circle.service;

import app.circle.dto.ProfileDto;
import app.circle.entity.User;
import app.circle.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SettingsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SettingsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateUserNickname(UUID userId, String newNickname) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setNickname(newNickname);
            return userRepository.save(user);
        }
        return null;
    }

    public User updateProfilePhoto(UUID userId, String profilePhoto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfilePhoto(profilePhoto);
            return userRepository.save(user);
        }
        return null;
    }

    public User updateUser(UUID userId, ProfileDto profileDto){
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEmail(profileDto.getEmail());
            user.setPhoneNumber(profileDto.getPhoneNumber());
            user.setNickname(profileDto.getNickname());
            user.setProfilePhoto(profileDto.getPhoneNumber());

            return userRepository.save(user);
        }

        return null;
    }

    public List<User> searchByNickname(String searchKeywords) {
        String[] keywords = searchKeywords.split("\\s+");
        List<User> users = new ArrayList<>();

        for (String keyword : keywords) {
            List<User> foundUsers = userRepository.findByNicknameContainingIgnoreCase(keyword);
            // Burada, aynı kullanıcılar tekrar eklenmemesi için bir kontrol yapılabilir
            users.addAll(foundUsers);
        }
        return users;
    }


    public User resetUserPassword(UUID userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Şifreyi hashle
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            return userRepository.save(user);
        }
        return null;
    }



}
