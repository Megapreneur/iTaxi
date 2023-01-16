package com.semicolon.itaxi.services;


import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.TokenVerification;
import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.data.models.Vehicle;
import com.semicolon.itaxi.data.models.enums.Authority;
import com.semicolon.itaxi.data.models.enums.DriverStatus;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.data.repositories.TokenVerificationRepository;
import com.semicolon.itaxi.data.repositories.TripRepository;
import com.semicolon.itaxi.data.repositories.VehicleRepository;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterVehicleRequest;
import com.semicolon.itaxi.dto.response.BookingResponse;
import com.semicolon.itaxi.dto.response.LoginDriverResponse;
import com.semicolon.itaxi.dto.response.RegisterDriverResponse;
import com.semicolon.itaxi.dto.response.RegisterVehicleResponse;
import com.semicolon.itaxi.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.semicolon.itaxi.utils.ValidateEmail.isValidEmail;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService{

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailNotificationService notificationService;

    @Autowired
    private TokenVerificationRepository tokenVerificationRepository;
    @Autowired
    private PersonService personService;




    @Override
    public RegisterDriverResponse register(RegisterDriverRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        if (isValidEmail(request.getEmail().toLowerCase())){
            if (driverRepository.existsByEmail(request.getEmail().toLowerCase())) throw  new UserExistException("User Already Exist");
            if(request.getPassword().equals(request.getConfirmPassword())) {
                Driver driver = modelMapper.map(request, Driver.class);
                driver.setPassword(passwordEncoder.encode(request.getPassword()));
                driver.setEmail(request.getEmail().toLowerCase());
                driver.getAuthority().add(Authority.DRIVER);
                Driver savedDrive = driverRepository.save(driver);
                String otp = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));
                TokenVerification newToken = new TokenVerification();
                newToken.setToken(otp);
                newToken.setUserEmail(request.getEmail().toLowerCase());
                tokenVerificationRepository.save(newToken);
                notificationService.sendWelcomeMessageToDriver(request,otp);
                return RegisterDriverResponse
                        .builder()
                        .message("Hello " + savedDrive.getName() + " , Your registration was successful")
                        .build();
            }
            throw new MismatchedPasswordException("Password does not match!!!");
        }
        throw new InvalidEmailException("This email is not valid");
    }

    @Override
    public void verifyDriver(String token) throws ITaxiException {
        TokenVerification savedToken = tokenVerificationRepository.findByToken(token)
                .orElseThrow(() -> new ITaxiException("Token is invalid"));

        Optional<Driver> driver = driverRepository.findByEmail(savedToken.getUserEmail().toLowerCase());
        Calendar calendar = Calendar.getInstance();
        if((savedToken.getExpiresAt().getTime() - calendar.getTime().getTime()) <= 0){
            tokenVerificationRepository.delete(savedToken);
            String newOtp = personService.generateToken(driver.get().getEmail());
            notificationService.newTokenMail(driver.get().getEmail().toLowerCase(), newOtp);
            log.warn("Token has expired, please check your email for another token");
            throw new ITaxiException("Token has expired, please check your email for another token");
        }else{
            driver.get().setEnabled(true);
            driverRepository.save(driver.get());
        }
        tokenVerificationRepository.delete(savedToken);
    }

    @Override
    public void driverForgetPassword(String email) throws ITaxiException {
        Optional<Driver> driver = Optional.ofNullable(driverRepository.findByEmail(email.toLowerCase()).orElseThrow(() ->
                new ITaxiException("User with this email does not exist")));
        if (driver.isPresent()){
            String newOtp = personService.generateToken(email);
            notificationService.sendResetPasswordMail(email.toLowerCase(), newOtp);
        }
        throw new ITaxiException("User with this email does not exist");
    }

    @Override
    public void verifyForgetPasswordDriver(String token, String password) throws ITaxiException {
        TokenVerification savedToken = tokenVerificationRepository.findByToken(token)
                .orElseThrow(() -> new ITaxiException("token does not exist"));

        Optional<Driver> driver = driverRepository.findByEmail(savedToken.getUserEmail().toLowerCase());

        Calendar cal = Calendar.getInstance();
        if((savedToken.getExpiresAt().getTime() - cal.getTime().getTime()) <= 0){
            tokenVerificationRepository.delete(savedToken);
            String newOtp = personService.generateToken(driver.get().getEmail().toLowerCase());
            notificationService.sendResetPasswordMail(driver.get().getEmail().toLowerCase(), newOtp);
            log.warn("Token has expired, please check your email for another token");
        }else{
            driver.get().setEnabled(true);
            driverRepository.save(driver.get());
        }
    }

    @Override
    public Driver getDriver(String location) throws NoDriverFoundException {
        List<Driver> drivers = driverRepository.findByLocation(location);
        List<Driver> availableDriver = new ArrayList<>();
        for (Driver driver: drivers) {
            if (driver.getDriverStatus().equals(DriverStatus.AVAILABLE)){
                availableDriver.add(driver);
            }
        }
        if (!availableDriver.isEmpty()){
            SecureRandom random = new SecureRandom();
            return drivers.get(random.nextInt(drivers.size()));
        }
        throw new NoDriverFoundException("No driver available at your location");
    }

    @Override
    public RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail().toLowerCase());
        if (driver.isPresent()){
            Optional<Vehicle> savedVehicle = vehicleRepository.findByDriverId(driver.get().getId());
            if (savedVehicle.isEmpty()){
                Vehicle vehicle = Vehicle
                        .builder()
                        .driver(driver.get())
                        .carColour(request.getCarColour())
                        .carModel(request.getCarModel())
                        .carNumber(request.getCarNumber())
                        .build();
                vehicleRepository.save(vehicle);
                return RegisterVehicleResponse
                        .builder()
                        .message(driver.get().getName() + " your car has been registered successfully. Safe trips")
                        .build();
            }
            throw new InvalidActionException("You can't register more than a car !!!");
        }
            throw new InvalidDriverException("Invalid Driver details");
    }

    @Override
    public LoginDriverResponse login(LoginDriverRequest request) throws InvalidDriverException{
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail().toLowerCase());
        if (driver.isPresent() && passwordEncoder.matches(request.getPassword(), driver.get().getPassword())){
            driver.get().setDriverStatus(request.getDriverStatus());
            driver.get().setLocation(request.getLocation());
            driverRepository.save(driver.get());
            return LoginDriverResponse
                    .builder()
                    .message("Welcome back " + driver.get().getName() + ". Ready to go for some rides ?")
                    .build();
        }
            throw new InvalidDriverException("Invalid Driver details");
    }

    @Override
    public List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException {
        Optional<Driver> savedDriver = driverRepository.findByEmail(email.toLowerCase());
        if (savedDriver.isPresent()){
            List<Trip> tripHistory = tripRepository.findTripsByDriver(savedDriver.get());
            if (!tripHistory.isEmpty()){
                return tripHistory;
            }
            throw new NoTripHistoryForUserException("You have no trip history");
        }
        throw new NoTripHistoryForUserException("Invalid login details");
    }

    @Override
    public BookingResponse bookingDetails() {
        return null;
    }

    @Override
    public Vehicle getVehicleByDriver(Driver driver) {
        return vehicleRepository.findByDriver(driver)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }



//
//    @Override
//    public PaymentResponse payment(PaymentRequest request) {
//        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
//        if (driver.isPresent()){
//            Payment payment = Payment
//                    .builder()
//                    .paymentType(request.getPaymentType())
//                    .amount(request.getAmount())
//                    .userEmail(request.getUserEmail())
//                    .build();
//            Payment savedPayment = paymentRepository.save(payment);
//
//            PaymentResponse response = new PaymentResponse();
//            response.setMessage(savedPayment);
//
//
//        }
//        throw new InvalidUserException("Invalid Email") ;
//    }
}
