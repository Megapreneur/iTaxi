package com.semicolon.itaxi.security.provider;

import com.semicolon.itaxi.data.models.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ITaxiAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user;
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
