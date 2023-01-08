package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.*;
import com.semicolon.itaxi.data.repositories.AdminRepository;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.data.repositories.TokenVerificationRepository;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService{
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenVerificationRepository tokenVerificationRepository;

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
        if(admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) return response(admin.get());
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) return response(user.get());
        throw new UsernameNotFoundException("Invalid User Details");
    }

    @Override
    public void forgetPassword(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email.toLowerCase());
        if (admin.isPresent()){
            String otp = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));
            TokenVerification newToken = new TokenVerification();
            newToken.setToken(otp);
            newToken.setUserEmail(email);
            tokenVerificationRepository.save(newToken);

        }
        Optional<User> user = userRepository.findByEmail(email);

    }

    private LoginUserResponse response(Person person){
        return LoginUserResponse.builder()
                .message(person.getName()+ " , you are logged in successfully")
                .build();
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
