package app.circle.service;


import app.circle.dto.SignupRequest;
import app.circle.entity.User;
import app.circle.exceptions.RegistrationException;
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
        // E-posta veya telefon numarası zaten kayıtlı mı kontrol et

        if ("email".equalsIgnoreCase(signupRequest.getSignupType()) && userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RegistrationException("Email address is already registered");
        }

        if ("phone".equalsIgnoreCase(signupRequest.getSignupType()) && userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber())) {
            throw new RegistrationException("Phone Number is already registered.");
        }

        if ("both".equalsIgnoreCase(signupRequest.getSignupType())) {
            if (userRepository.existsByEmail(signupRequest.getEmail()) || userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber())) {
                throw new RegistrationException("Email address or Phone Number is already registered");
            }
        }

        User customer = new User();
        BeanUtils.copyProperties(signupRequest, customer);

        customer.setRole("ROLE_USER");
        // Şifreyi hashlemeden önce
        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        customer.setPassword(hashedPassword);

        User createdCustomer = userRepository.save(customer);
        customer.setId(createdCustomer.getId());
        return customer;
    }
}

// asldjblajsdbljabsd
// nmzxbcmnxzbcmnzbxcmnzc
// qpoweıqpoweıpwoqıe