package app.circle.controller;

import app.circle.dto.LoginRequest;
import app.circle.dto.LoginResponse;
import app.circle.dto.SignupRequest;
import app.circle.dto.SignupResponse;
import app.circle.entity.User;
import app.circle.service.AuthService;
import app.circle.service.jwt.UserServiceImpl;
import app.circle.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;

    private final JwtUtils jwtUtil;

    private final AuthService authService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtUtils jwtUtil, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmailOrPhoneNumber());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, loginRequest.getPassword(), userDetails.getAuthorities()));
        } catch (BadCredentialsException e) {

            throw new BadCredentialsException("Incorrect email/phone or password.");
        } catch (DisabledException disabledException) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponse(jwt);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = new SignupResponse();

        User createdUser = authService.createUser(signupRequest);
        if (createdUser != null) {
            String token = jwtUtil.generateToken(createdUser.getUsername());
            signupResponse.setId(createdUser.getId());
            signupResponse.setEmail(createdUser.getEmail());
            signupResponse.setPhoneNumber(createdUser.getPhoneNumber());
            signupResponse.setToken(token);

            return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user");
        }
    }
}
