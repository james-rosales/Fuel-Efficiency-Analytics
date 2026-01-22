package com.example.microservice.config; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Import this

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF (important for development and simple API services)
            .csrf(AbstractHttpConfigurer::disable) 
            
            // 2. Configure Authorization: Permit ALL requests
            // This is the CRITICAL step to resolve the redirect loop, as it stops 
            // Spring Security from forcing a login page redirect.
            .authorizeHttpRequests(authorize -> authorize
                // Allow all requests to all paths, including '/', '/analytics/dashboard', 
                // '/calculate', and all API paths.
                .anyRequest().permitAll()
            )
            
            // 3. Remove/Disable Form Login (prevents redirect loops)
            .formLogin(AbstractHttpConfigurer::disable)
            
            // 4. Remove/Disable Logout (prevents unnecessary processing)
            .logout(AbstractHttpConfigurer::disable);
            
        return http.build();
    }
}