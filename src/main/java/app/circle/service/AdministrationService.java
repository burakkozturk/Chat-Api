package app.circle.service;

import app.circle.entity.BanList;
import app.circle.entity.User;
import app.circle.repository.BanListRepository;
import app.circle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdministrationService {

    private final UserRepository userRepository;
    private final BanListRepository banListRepository;

    public AdministrationService(UserRepository userRepository, BanListRepository banListRepository) {
        this.userRepository = userRepository;
        this.banListRepository = banListRepository;
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID userId){

        return userRepository.findById(userId);
    }


    public List<BanList> getAllBannedUsers(){
        return banListRepository.findAll();
    }


    public BanList banUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        BanList ban = new BanList();
        ban.setUser(user);
        return banListRepository.save(ban);
    }

    public void unbanUser(Long banId) {
        banListRepository.deleteById(banId);
    }
}
