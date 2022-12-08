package com.semicolon.itaxi.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.security.jwt.JwtUtil;
import com.semicolon.itaxi.security.manager.ITaxiAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RequiredArgsConstructor
public class ITaxiAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ITaxiAuthenticationManager iTaxiAuthenticationManager;
    ObjectMapper mapper = new ObjectMapper();
    private final JwtUtil jwt;

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        User user;
        try {
            user = mapper.readValue(request.getReader(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String accessToken = jwt.generateAccessToken(userDetails);
        String generateRefreshToken = jwt.generateRefreshTokens(userDetails);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", generateRefreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
    }
}
