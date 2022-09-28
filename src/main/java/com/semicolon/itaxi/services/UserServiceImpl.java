package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.*;
import com.semicolon.itaxi.data.repositories.TripRepository;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.data.repositories.VehicleRepository;
import com.semicolon.itaxi.dto.requests.*;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    private final TripRepository tripRepository;
    @Autowired
    private DriverService driverService;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException {
        if (userRepository.existsByEmail(request.getEmail()))throw  new UserExistException("User Already Exist", HttpStatus.FORBIDDEN);
        User user = User
                .builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .confirmPassword(request.getConfirmPassword())
                .build();
        if (request.getPassword().equals(request.getConfirmPassword())){
            User savedUser = userRepository.save(user);
            return RegisterUserResponse
                    .builder()
                    .message( "Hello " + savedUser.getName() + " , Your registration was successful")
                    .build();
        }
        throw new MismatchedPasswordException("Password does not match!!!", HttpStatus.FORBIDDEN);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) throws InvalidUserException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()){
            if (user.get().getPassword().equals(request.getPassword())){
                return LoginUserResponse
                        .builder()
                        .message("Welcome back " + user.get().getName() + ". Where will you like to go today?")
                        .build();
            }
            throw new InvalidUserException("Invalid login details!!!", HttpStatus.NOT_ACCEPTABLE);
        }
        throw new InvalidUserException("Invalid login details!!!", HttpStatus.NOT_FOUND);
    }

    @Override
    public BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException, UserExistException {
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        if (savedUser.isPresent()){
            Driver assignedDriver = driverService.getDriver(request.getLocation());
            Trip trip = Trip
                    .builder()
                    .dropOffAddress(request.getDropOffAddress())
                    .driver(assignedDriver)
                    .time(LocalDateTime.now())
                    .pickUpAddress(request.getPickUpAddress())
                    .user(savedUser.get())
                    .location(request.getLocation())
                    .build();
            Trip saved = tripRepository.save(trip);
            return getBookTripResponse(assignedDriver, saved);
        }

        throw new UserExistException("User does not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(TripHistoryRequest request) {
        Optional<User> savedUser = userRepository.findByEmail(request)
        return null;
    }

    private BookTripResponse getBookTripResponse(Driver assignedDriver, Trip saved) throws UserExistException {
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByDriver(assignedDriver);
        if(vehicle.isPresent()){

            return BookTripResponse.builder()
                    .message("Your trip has been booked successfully")
                    .name(saved.getDriver().getName())
                    .phoneNumber(saved.getDriver().getPhoneNumber())
                    .model(vehicle.get().getCarModel())
                    .color(vehicle.get().getCarColour())
                    .vehicleNumber(vehicle.get().getCarNumber())
                    .dateOfRide(saved.getTime())
                    .build();
        }
        throw new UserExistException("Vehicle is faulty", HttpStatus.NOT_FOUND);
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public UserResponse feedback(String message) {
        return null;
    }

//    @Override
//    public BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException {
//        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
//        if (savedUser.isPresent()){
//            savedUser.get().setPickUpAddress(request.getPickUpAddress());
//            savedUser.get().setDropOffAddress(request.getDropOffAddress());
//            BookTripResponse response = new BookTripResponse();
//            response.setMessage("You have been connected to " + driverService.getDriver(request.getLocation())
//                    +". Your trip from " + savedUser.get().getPickUpAddress() + " to "
//                    + savedUser.get().getDropOffAddress() + " was ordered at " + response.getDateOfRide());
//            return response ;
//        }
//        throw new InvalidUserException("Invalid Email") ;
//    }
//
//    @Override
//    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
//        Optional<User> savedUser = userRepository.findByEmail(paymentRequest.getEmail());
//        if (savedUser.isPresent()){
//            Payment payment = new Payment();
//            payment.setUserEmail(payment.getUserEmail());
//            payment.setPaymentType(paymentRequest.getPaymentType());
//            payment.setAmount(paymentRequest.getAmount());
//
//
//        }
//        return null;
//    }
//
//    @Override
//    public UserResponse feedback(String message) {
//
//        return null;
//    }

}
