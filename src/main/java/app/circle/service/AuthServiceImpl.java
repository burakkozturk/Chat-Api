package app.circle.service;


import app.circle.dto.SignupRequest;
import app.circle.entity.User;
import app.circle.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User createCustomer(SignupRequest signupRequest) {
        //Check if customer already exist
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return null;
        }

        User customer = new User();
        BeanUtils.copyProperties(signupRequest,customer);

        //Hash the password before saving
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        customer.setPassword(hashPassword);
        User createdCustomer = userRepository.save(customer);
        customer.setId(createdCustomer.getId());
        return customer;
    }
}
