package com.semicolon.itaxi.security.config;

import com.semicolon.itaxi.security.filter.ITaxiAuthenticationFilter;
import com.semicolon.itaxi.security.filter.ITaxiAuthorizationFilter;
import com.semicolon.itaxi.security.jwt.JwtUtil;
import com.semicolon.itaxi.security.manager.ITaxiAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
        filter.setFilterProcessesUrl("/api/v1/iTaxi/login");

        return http.cors().and().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/iTaxi/admin/register", "/api/v1/iTaxi/user/register",
                        "/api/v1/iTaxi/driver/register")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/iTaxi/user/verify?token",
                        "/api/v1/iTaxi/user/forget-password",
                        "/api/v1/iTaxi/user/verify-user-forget-password",
                        "/api/v1/iTaxi/user/orderRide",
                        "/api/v1/iTaxi/user/userTripHistory/{email}",
                        "/api/v1/iTaxi/user/payment")
                .hasAnyAuthority("USER")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/iTaxi/driver/login",
                        "/api/v1/iTaxi/driver/verify?token",
                        "/api/v1/iTaxi/driver/forget-password",
                        "/api/v1/iTaxi/driver/verify-driver-forget-password",
                        "/api/v1/iTaxi/driver/registerYourCar",
                        "/api/v1/iTaxi/driver/driver/trips/{email}")
                .hasAnyAuthority("DRIVER")
                .and()
                .addFilter(filter)
                .addFilterBefore(new ITaxiAuthorizationFilter(), ITaxiAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }

}
