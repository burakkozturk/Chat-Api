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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Write logic to fetch customer from DB
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), Collections.emptyList());
    }
}