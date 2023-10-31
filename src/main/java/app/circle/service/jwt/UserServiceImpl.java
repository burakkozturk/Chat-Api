package app.circle.service.jwt;

import app.circle.entity.User;
import app.circle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
        User customer = userRepository.findByEmail(emailOrPhoneNumber)
                .orElseGet(() -> userRepository.findByPhoneNumber(emailOrPhoneNumber)
                        .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email or phone number: " + emailOrPhoneNumber)));

        return customer;
    }

    public UserDetails loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        User customer = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with phone number: " + phoneNumber));

        return customer;
    }
}