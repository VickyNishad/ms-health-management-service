package com.health.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.health.utility.JwtUtils;

@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private static final String[] SWAGGER_WHITELIST = {
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
        "/api-docs/**",
            "/api/v1/doctor/**",
            "/api/v1/clinic/**",
            "/api/v1/search/**"
        
    };

    private static final String[] PUBLIC_ENDPOINTS = {
    	"/api/v1/auth/**",
    	"/api/v1/master/**",
    	"/medicque/menu/**",
    	"/api/v1/otp/**"

    };

    public SecurityConfig(JwtUtils jwtUtils, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.jwtUtils = jwtUtils;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SWAGGER_WHITELIST).permitAll() 
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()  
                .anyRequest().authenticated()             
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}

