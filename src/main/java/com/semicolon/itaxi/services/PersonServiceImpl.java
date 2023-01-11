package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.*;
import com.semicolon.itaxi.data.repositories.*;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import com.semicolon.itaxi.exceptions.ITaxiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.InvalidTransactionException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j

public class PersonServiceImpl implements PersonService{
    private final NotificationRepository notificationRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationService notificationService;
    private final TokenVerificationRepository tokenVerificationRepository;

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
        if(admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) return response(admin.get());
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) return response(user.get());
        throw new UsernameNotFoundException("Invalid User Details");
    }

//    @Override
//    public void verifyUser(String token) throws ITaxiException {
//        TokenVerification savedToken = tokenVerificationRepository.findByToken(token)
//                .orElseThrow(() -> new ITaxiException("Token is invalid"));
//        Optional<User> user = userRepository.findByEmail(savedToken.getUserEmail());
//        Calendar calendar = Calendar.getInstance();
//        if((savedToken.getExpiresAt().getTime() - calendar.getTime().getTime()) <= 0){
//            tokenVerificationRepository.delete(savedToken);
//            String newOtp = generateToken(user.get().getEmail());
//            notificationService.newTokenMail(user.get().getEmail(), newOtp);
//            log.warn("Token has expired, please check your email for another token");
//            throw new ITaxiException("Token has expired, please check your email for another token");
//        }else{
//            user.get().setEnabled(true);
//            userRepository.save(user.get());
//        }
//        tokenVerificationRepository.delete(savedToken);
//    }

//    @Override
//    public void verifyDriver(String token) throws ITaxiException {
//        TokenVerification savedToken = tokenVerificationRepository.findByToken(token)
//                .orElseThrow(() -> new ITaxiException("Token is invalid"));
//
//        Optional<Driver> driver = driverRepository.findByEmail(savedToken.getUserEmail());
//        Calendar calendar = Calendar.getInstance();
//        if((savedToken.getExpiresAt().getTime() - calendar.getTime().getTime()) <= 0){
//            tokenVerificationRepository.delete(savedToken);
//            String newOtp = generateToken(driver.get().getEmail());
//            notificationService.newTokenMail(driver.get().getEmail(), newOtp);
//            log.warn("Token has expired, please check your email for another token");
//            throw new ITaxiException("Token has expired, please check your email for another token");
//        }else{
//            driver.get().setEnabled(true);
//            driverRepository.save(driver.get());
//        }
//        tokenVerificationRepository.delete(savedToken);
//    }


    @Override
    public void forgetPassword(String email) throws ITaxiException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email.toLowerCase()).orElseThrow(() ->
                new ITaxiException("User with this email does not exist")));
        if (user.isPresent()){
            String newOtp = generateToken(email);
            notificationService.sendResetPasswordMail(email.toLowerCase(), newOtp);
        }
        throw new ITaxiException("User with this email does not exist");
    }





    public String generateToken(String email) {
        String otp = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));
        TokenVerification newToken = new TokenVerification();
        newToken.setToken(otp);
        newToken.setUserEmail(email.toLowerCase());
        tokenVerificationRepository.save(newToken);
        return otp;
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
