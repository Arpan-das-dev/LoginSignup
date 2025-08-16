package com.loginSignupAuth.loginSignupAuth.Controller;

import com.loginSignupAuth.loginSignupAuth.Dto.AuthResponse;
import com.loginSignupAuth.loginSignupAuth.Dto.LoginRequest;
import com.loginSignupAuth.loginSignupAuth.Dto.SignUpRequest;
import com.loginSignupAuth.loginSignupAuth.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PutMapping("/signup")
    public ResponseEntity<String> signup(SignUpRequest request){
        authService.signup(request);
        return ResponseEntity.ok("Successfully Registered");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

}
