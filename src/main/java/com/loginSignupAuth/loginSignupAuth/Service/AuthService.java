package com.loginSignupAuth.loginSignupAuth.Service;

import com.loginSignupAuth.loginSignupAuth.Dto.AuthResponse;
import com.loginSignupAuth.loginSignupAuth.Dto.LoginRequest;
import com.loginSignupAuth.loginSignupAuth.Dto.SignUpRequest;
import com.loginSignupAuth.loginSignupAuth.Entity.User;
import com.loginSignupAuth.loginSignupAuth.Repository.UserRepository;
import com.loginSignupAuth.loginSignupAuth.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager manager;
    private final PasswordEncoder encoder;
    private final JwtService service;
    private final UserRepository repository;

    public void signup(SignUpRequest request) {
        boolean exist = repository.existsByEmail(request.getEmail());
        if (exist) throw new IllegalArgumentException("User Already exists");

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        repository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        manager.authenticate(token);
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(()->new IllegalArgumentException("invalid credentials"));
        UserDetails details = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
        String webToken = service.token(details);
        String fullName = user.getFirstName()+" "+user.getLastName();
        return new AuthResponse(fullName,webToken);
    }


}
