package com.semicolon.itaxi.security.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.semicolon.itaxi.security.jwt.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.issuer}")
    private String issuer;

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(issuer, Algorithm.HMAC512(secret));
    }
}
