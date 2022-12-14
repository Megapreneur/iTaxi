package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.*;
import com.semicolon.itaxi.data.repositories.PaymentRepository;
import com.semicolon.itaxi.data.repositories.TokenVerificationRepository;
import com.semicolon.itaxi.data.repositories.TripRepository;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.*;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import static com.semicolon.itaxi.utils.ValidateEmail.isValidEmail;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DriverService driverService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenVerificationRepository tokenVerificationRepository;

    @Autowired
    private EmailNotificationService notificationService;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        if (isValidEmail(request.getEmail())){
            if (userRepository.existsByEmail(request.getEmail().toLowerCase()))throw new UserExistException("User Already Exist", HttpStatus.FORBIDDEN);
            if (request.getPassword().equals(request.getConfirmPassword())){
                User user = modelMapper.map(request, User.class);
                user.setEmail(request.getEmail().toLowerCase());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                User savedUser = userRepository.save(user);
                String otp = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));
                TokenVerification newToken = new TokenVerification();
                newToken.setToken(otp);
                newToken.setUserEmail(request.getEmail().toLowerCase());
                tokenVerificationRepository.save(newToken);
                notificationService.sendWelcomeMessageToCustomer(request, otp);
                return RegisterUserResponse
                        .builder()
                        .message( "Hello " + savedUser.getName() + " , Your registration was successful")
                        .build();
            }
            throw new MismatchedPasswordException("Password does not match!!!", HttpStatus.FORBIDDEN);
        }
        throw new InvalidEmailException("This email address is invalid!", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException, UserExistException {
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail().toLowerCase());
        if (savedUser.isPresent()){
            Driver assignedDriver = driverService.getDriver(request.getLocation());
            Trip trip = modelMapper.map(request, Trip.class);
            trip.setDriver(assignedDriver);
            trip.setUser(savedUser.get());
            Trip saved = tripRepository.save(trip);
            Vehicle vehicle = driverService.getVehicleByDriver(assignedDriver);
            return getBookTripResponse(assignedDriver ,saved, vehicle);
        }
        throw new UserExistException("User does not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException {
        Optional<User> savedUser = userRepository.findByEmail(email.toLowerCase());
        if (savedUser.isPresent()){
            List<Trip> userTripHistory = tripRepository.findTripsByUser(savedUser.get());
            if (!userTripHistory.isEmpty()){
                return userTripHistory;
            }
            throw new NoTripHistoryForUserException("You have no trip history", HttpStatus.NOT_FOUND);
        }
        throw new NoTripHistoryForUserException("You have no trip history", HttpStatus.NOT_FOUND);
    }

    private BookTripResponse getBookTripResponse(Driver driver, Trip savedTrip, Vehicle vehicle){
        return  BookTripResponse.builder()
                .message("The have been connected to a driver")
                .driverName(driver.getName())
                .phoneNumber(driver.getPhoneNumber())
                .dateOfRide(savedTrip.getTime())
                .carModel(vehicle.getCarModel())
                .vehicleNumber(vehicle.getCarNumber())
                .carColor(vehicle.getCarColour())
                .build();
    }
    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) throws NoTripHistoryForUserException {
        List<Trip> trips = getHistoryOfAllTrips(paymentRequest.getEmail().toLowerCase());
        if (!trips.isEmpty()){
            Trip trip = trips.get(trips.size() - 1);
            Payment payment = modelMapper.map(paymentRequest, Payment.class);
            payment.setTrip(trip);
            payment.setUser(trip.getUser());
//            payment.setDriver(trip.getDriver());
            Payment savedPayment = paymentRepository.save(payment);
            return PaymentResponse
                    .builder()
                    .message("Your payment of ???" + savedPayment.getAmount() + " for the trip from " +
                            trip.getPickUpAddress() + " to " + trip.getDropOffAddress() + " was successful!")
                    .build();
        }
        throw new NoTripHistoryForUserException("You have no trip history", HttpStatus.NOT_FOUND);
    }

    @Override
    public UserResponse feedback(String message) {
        return null;
    }


}
