package com.semicolon.itaxi.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ITaxiAuthorizationFilter extends OncePerRequestFilter {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (request.getServletPath().equals("/api/v1/iTaxi/login") || request.getServletPath().equals("/api/v1/iTaxi/driver/login")){
            filterChain.doFilter(request, response);
        }else{
            if (authHeader != null && authHeader.startsWith("Bearer ")){
                try {
                    String token = authHeader.substring("Bearer ".length());
                    JWTVerifier verifier = JWT.require(Algorithm.HMAC512("this-is-my-application"))
                            .build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String subject = decodedJWT.getSubject();
                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(subject, null,
                            roles
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                }catch (Exception e) {
                    response.setContentType(APPLICATION_JSON_VALUE);
                    mapper.writeValue(response.getOutputStream(), e.getMessage());
                }
            }else filterChain.doFilter(request, response);
        }
    }
}
