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
    public User createUser(SignupRequest signupRequest) {
        //Check if customer already exist
        if (("email".equalsIgnoreCase(signupRequest.getSignupType()) && userRepository.existsByEmail(signupRequest.getEmail())) ||
                ("phone".equalsIgnoreCase(signupRequest.getSignupType()) && userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber()))) {
            return null;
        }

        User customer = new User();
        BeanUtils.copyProperties(signupRequest, customer);

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        customer.setPassword(hashedPassword);

        User createdCustomer = userRepository.save(customer);
        customer.setId(createdCustomer.getId());
        return customer;
    }
}