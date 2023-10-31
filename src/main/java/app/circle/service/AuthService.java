package app.circle.service;

import app.circle.dto.SignupRequest;
import app.circle.entity.User;

public interface AuthService {
    User createCustomer(SignupRequest signupRequest);
}
