package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Admin;
import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.Person;
import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.data.repositories.AdminRepository;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService{
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
        if(admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) return response(admin.get());
    }


    @Override
    public Person getPersonByUsername(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if(admin.isPresent()) return admin.get();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) return user.get();
        Optional<Driver> driver = driverRepository.findByEmail(email);
        if (driver.isPresent()) return driver.get();

        throw new UsernameNotFoundException("User not found");

    }
}
