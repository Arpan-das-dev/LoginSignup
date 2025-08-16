package com.loginSignupAuth.loginSignupAuth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(); // it will be used to store password in hash format
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

            security.
                    csrf(csrf->csrf.disable())
                    .authorizeHttpRequests(auth-> auth
                            .requestMatchers("api/auth/**").permitAll()
                            .anyRequest().authenticated())
                    .formLogin(Customizer.withDefaults());
            return security.build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
