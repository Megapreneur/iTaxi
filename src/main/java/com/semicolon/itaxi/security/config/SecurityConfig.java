package com.semicolon.itaxi.security.config;

import com.semicolon.itaxi.security.filter.ITaxiAuthenticationFilter;
import com.semicolon.itaxi.security.jwt.JwtUtil;
import com.semicolon.itaxi.security.manager.ITaxiAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    public final ITaxiAuthenticationManager iTaxiAuthenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        UsernamePasswordAuthenticationFilter filter = new ITaxiAuthenticationFilter(iTaxiAuthenticationManager, jwtUtil);
        filter.setFilterProcessesUrl("");

        return http.cors().and().cors().disable().build();
    }

}
