package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.*;
import com.semicolon.itaxi.data.models.enums.Authority;
import com.semicolon.itaxi.data.repositories.PaymentRepository;
import com.semicolon.itaxi.data.repositories.TokenVerificationRepository;
import com.semicolon.itaxi.data.repositories.TripRepository;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.BookTripResponse;
import com.semicolon.itaxi.dto.response.PaymentResponse;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.dto.response.UserResponse;
import com.semicolon.itaxi.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.semicolon.itaxi.utils.ValidateEmail.isValidEmail;

@Service
@Slf4j
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

    @Autowired
    private PersonService personService;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        if (isValidEmail(request.getEmail())){
            if (userRepository.existsByEmail(request.getEmail().toLowerCase()))throw new UserExistException("User Already Exist");
            if (request.getPassword().equals(request.getConfirmPassword())){
                User user = modelMapper.map(request, User.class);
                user.setEmail(request.getEmail().toLowerCase());
                user.getAuthority().add(Authority.USER);
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
            throw new MismatchedPasswordException("Password does not match!!!");
        }
        throw new InvalidEmailException("This email address is invalid!");
    }

    @Override
    public void verifyUser(String token) throws ITaxiException {
        TokenVerification savedToken = tokenVerificationRepository.findByToken(token)
                .orElseThrow(() -> new ITaxiException("Token is invalid"));
        Optional<User> user = userRepository.findByEmail(savedToken.getUserEmail());
        Calendar calendar = Calendar.getInstance();
        if((savedToken.getExpiresAt().getTime() - calendar.getTime().getTime()) <= 0){
            tokenVerificationRepository.delete(savedToken);
            String newOtp = personService.generateToken(user.get().getEmail());
            notificationService.newTokenMail(user.get().getEmail(), newOtp);
            log.warn("Token has expired, please check your email for another token");
            throw new ITaxiException("Token has expired, please check your email for another token");
        }else{
            user.get().setEnabled(true);
            userRepository.save(user.get());
        }
        tokenVerificationRepository.delete(savedToken);
    }
    @Override
    public void verifyForgetPasswordUser(String token, String password) throws ITaxiException {
        TokenVerification userToken = tokenVerificationRepository.findByToken(token)
                .orElseThrow(() -> new ITaxiException("token does not exist"));

        Optional<User> user = userRepository.findByEmail(userToken.getUserEmail().toLowerCase());

        Calendar cal = Calendar.getInstance();
        if((userToken.getExpiresAt().getTime() - cal.getTime().getTime()) <= 0){
            tokenVerificationRepository.delete(userToken);
            String newOtp = personService.generateToken(user.get().getEmail().toLowerCase());
            notificationService.sendResetPasswordMail(user.get().getEmail(), newOtp);
            log.warn("Token has expired, please check your email for another token");
        }else{
            user.get().setEnabled(true);
            userRepository.save(user.get());
        }
    }

    @Override
    public void userForgetPassword(String email) throws ITaxiException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email.toLowerCase()).orElseThrow(() ->
                new ITaxiException("User with this email does not exist")));
        if (user.isPresent()){
            String newOtp = personService.generateToken(email);
            notificationService.sendResetPasswordMail(email.toLowerCase(), newOtp);
        }
        throw new ITaxiException("User with this email does not exist");
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
        throw new UserExistException("User does not exist");
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException {
        Optional<User> savedUser = userRepository.findByEmail(email.toLowerCase());
        if (savedUser.isPresent()){
            List<Trip> userTripHistory = tripRepository.findTripsByUser(savedUser.get());
            if (!userTripHistory.isEmpty()){
                return userTripHistory;
            }
            throw new NoTripHistoryForUserException("You have no trip history");
        }
        throw new NoTripHistoryForUserException("You have no trip history");
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
                    .message("Your payment of ₦" + savedPayment.getAmount() + " for the trip from " +
                            trip.getPickUpAddress() + " to " + trip.getDropOffAddress() + " was successful!")
                    .build();
        }
        throw new NoTripHistoryForUserException("You have no trip history");
    }

    @Override
    public UserResponse feedback(String message) {
        return null;
    }


}
