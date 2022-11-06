package com.semicolon.itaxi.security.manager;

import com.semicolon.itaxi.security.provider.ITaxiAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ITaxiAuthenticationManager implements AuthenticationManager {

    private final ITaxiAuthenticationProvider iTaxiAuthenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
