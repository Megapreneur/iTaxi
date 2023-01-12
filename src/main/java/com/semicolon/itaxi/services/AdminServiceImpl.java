package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Admin;
import com.semicolon.itaxi.data.models.enums.Authority;
import com.semicolon.itaxi.data.repositories.AdminRepository;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.exceptions.InvalidEmailException;
import com.semicolon.itaxi.exceptions.MismatchedPasswordException;
import com.semicolon.itaxi.exceptions.UserExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.semicolon.itaxi.utils.ValidateEmail.isValidEmail;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        if (isValidEmail(request.getEmail())){
            if (adminRepository.existsByEmail(request.getEmail()))throw new UserExistException("Admin already Exist!!!");
            if (request.getPassword().equals(request.getConfirmPassword())){
                Admin admin = modelMapper.map(request, Admin.class);
                admin.getAuthority().add(Authority.ADMIN);
                admin.setPassword(passwordEncoder.encode(request.getPassword()));
                adminRepository.save(admin);

                return RegisterUserResponse.builder()
                        .message("Hello " + admin.getName() + " , Your registration was successful")
                        .build();
            }
            throw new MismatchedPasswordException("Password does not match!!!");
        }
        throw new InvalidEmailException("This email address is invalid!");
    }
}
