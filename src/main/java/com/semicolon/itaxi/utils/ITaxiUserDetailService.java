package com.semicolon.itaxi.utils;

import com.semicolon.itaxi.data.models.Person;
import com.semicolon.itaxi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ITaxiUserDetailService implements UserDetailsService {

    @Autowired
    private PersonService personService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.getPersonByUsername(username);
        return new SecureUser(person);
    }
}
