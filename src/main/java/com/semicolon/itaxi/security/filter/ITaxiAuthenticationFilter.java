package com.semicolon.itaxi.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.security.manager.ITaxiAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class ITaxiAuthenticationFilter {
    private final ITaxiAuthenticationManager iTaxiAuthenticationManager;
    ObjectMapper mapper = new ObjectMapper();

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user;
        user = mapper.readValue(request.getReader(), User.class);
        String email = user.getEmail();
        String password = user.getPassword();

        var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticatedToken = iTaxiAuthenticationManager.authenticate(authenticationToken);
        if (authenticatedToken != null){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticationToken);
            return authenticatedToken;
        }
        throw new BadCredentialsException("Invalid details!!!");
    }
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
}
