package app.circle.service;

import app.circle.dto.UserUpdateRequest;
import app.circle.entity.User;
import app.circle.exceptions.UserNotFoundException;
import app.circle.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public User updateNickname(UUID userId, String newNickname) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNickname(newNickname);
            userRepository.save(user);
            return user;
        }else {
            throw new UserNotFoundException("Kullanıcı bulunamadı");
        }
    }

    public void updatePassword(UUID userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public User updateUser(UUID userId, UserUpdateRequest userUpdateDTO) {
        User user = entityManager.find(User.class, userId);

        if (user != null) {
            if (userUpdateDTO.getEmail() != null) {
                user.setEmail(userUpdateDTO.getEmail());
            }
            if (userUpdateDTO.getNickname() != null) {
                user.setNickname(userUpdateDTO.getNickname());
            }
            if (userUpdateDTO.getPhoneNumber() != null) {
                user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
            }

            entityManager.merge(user);
            return user;
        } else {
            return null;
        }
    }
}
