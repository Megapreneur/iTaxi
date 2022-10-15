package com.semicolon.itaxi.services;

import com.semicolon.itaxi.config.SecureUser;
import com.semicolon.itaxi.data.models.*;
import com.semicolon.itaxi.data.repositories.PaymentRepository;
import com.semicolon.itaxi.data.repositories.TripRepository;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.data.repositories.VehicleRepository;
import com.semicolon.itaxi.dto.requests.*;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DriverService driverService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException {
        if (userRepository.existsByEmail(request.getEmail()))throw  new UserExistException("User Already Exist", HttpStatus.FORBIDDEN);
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
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
            DriverDto assignedDriver = driverService.getDriver(request.getLocation());
            Trip trip = modelMapper.map(request, Trip.class);
            Trip saved = tripRepository.save(trip);
            return getBookTripResponse(assignedDriver, saved);
        }
        throw new UserExistException("User does not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException {
        Optional<User> savedUser = userRepository.findByEmail(email);
        if (savedUser.isPresent()){
            List<Trip> userTripHistory = tripRepository.findTripsByUser(savedUser.get());
            if (!userTripHistory.isEmpty()){
                return userTripHistory;
            }
        }
        throw new NoTripHistoryForUserException("You have no trip history", HttpStatus.NOT_FOUND);
    }

    public BookTripResponse getBookTripResponse(DriverDto assignedDriver, Trip saved) throws UserExistException {
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
    public PaymentResponse makePayment(PaymentRequest paymentRequest) throws NoTripHistoryForUserException {
        List<Trip> trips = getHistoryOfAllTrips(paymentRequest.getEmail());
        if (!trips.isEmpty()){
            Trip trip = trips.get(trips.size() - 1);
            Payment payment = modelMapper.map(paymentRequest, Payment.class);
            Payment savedPayment = paymentRepository.save(payment);
            return PaymentResponse
                    .builder()
                    .message("Your payment of ₦" + savedPayment.getAmount() + " for the trip from " +
                            trip.getPickUpAddress() + " to " + trip.getDropOffAddress() + " was successful!")
                    .build();
        }
        return null;
    }

    @Override
    public UserResponse feedback(String message) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new SecureUser(user);
    }
}
