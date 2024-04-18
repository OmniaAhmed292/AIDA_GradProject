package com.example.aida.auth;

import com.example.aida.Entities.*;
import com.example.aida.Repositories.ConfirmationTokenRepository;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.UserRepository;
import com.example.aida.Repositories.VendorRepository;
import com.example.aida.config.JwtService;
import com.example.aida.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.Decimal128;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailValidator emailValidator;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailServiceImpl;
//    private final EmailService emailService;
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {
        //-----------------------------
        //  Validations
        //-----------------------------
        boolean isValidEmail=emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email is not valid");
        }

        //-----------------------------
        //  User Creation
        //-----------------------------
        var user = User.builder()
                .fname(request.getFname())
                .lname(request.getLname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(request.getUser_type())
                .isEnabled(true) //TODO: change to false when email verification is implemented
                .isAccountLocked(false)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("User already exists");
            throw new IllegalStateException("User already exists");
        }

        //-----------------------------
        //  Customer Creation
        //-----------------------------
        if(request.getUser_type().equals("customer")){
            var customerBuilder = Customer.builder()
                .fname(request.getFname())
                .lname(request.getLname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .customerSettings(new CustomerSettings(true, true, true, true))
                .balance(new Decimal128(BigDecimal.valueOf(0.0)))
                .points(0);
            if (request.getBirthdate() != null) {
                customerBuilder.birthdate(LocalDate.parse(request.getBirthdate()));
            }

            if (request.getPhoneNumber() != null) {
                customerBuilder.phoneNumber(request.getPhoneNumber());
            }

            if (request.getGender() != null) {
                customerBuilder.gender(request.getGender());
            }

            if (request.getAddress() != null) {
                Address address = new Address (request.getAddress().getCity(),
                        request.getAddress().getBuildingNo(),
                        request.getAddress().getApartmentNo(),
                        request.getAddress().getStreet()  );

                customerBuilder.address(address);
            }


            Customer customer = customerBuilder.build();
            customerRepository.save(customer);

        }

        //-----------------------------
        //  Vendor Creation
        //-----------------------------
        else if(request.getUser_type().equals("vendor")) {
            var vendorBuilder = Vendor.builder()
                    .fname(request.getFname())
                    .lname(request.getLname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .balance(new Decimal128(BigDecimal.valueOf(0.0)))
                    .settings(new VendorSettings(true,true));

            //TODO: this is required data, to be removed from IF statement When File Storage is implemented
            if (request.getBusinessInfo() != null) {
                BusinessInfo businessInfo = new BusinessInfo(
                        request.getBusinessInfo().getAboutUsInfo(),
                        request.getBusinessInfo().getBusinessType(),
                        request.getBusinessInfo().getBusinessName(),
                        LocalDate.now(),    //TODO set to a future fixed time
                        "December",
                        true) //TODO change to false When ADMIN verifies
                        ;
                vendorBuilder.businessInfo(businessInfo);
            }


            if (request.getPhoneNumber() != null) {
                vendorBuilder.phoneNumber(request.getPhoneNumber());
            }

            if (request.getAddress() != null) {
                Address address = new Address (request.getAddress().getCity(),
                        request.getAddress().getBuildingNo(),
                        request.getAddress().getApartmentNo(),
                        request.getAddress().getStreet()  );

                vendorBuilder.address(address);
            }


            Vendor vendor = vendorBuilder.build();
            vendorRepository.save(vendor);

        }
        var jwtToken = jwtService.generateToken(user);
        System.out.println(user);
        userRepository.save(user);
        SendValidationEmail(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void SendValidationEmail(User user) throws MessagingException {
        String newToken = generareAndSaveActivationToken(user);
//        send email
        emailServiceImpl.sendMail(
                user.getEmail(),
                "Activate your account",
                "Hello "+user.getFname()+" "+user.getLname()+"\n"+
                        "Thank you for signing up! Please use the following activation code to activate your account: \n"+newToken
                );
    }

    private String generareAndSaveActivationToken(User user) {
        String  generatedToken = generatreActivationCode(6);
//        System.out.println(userRepository.findByEmail(user.getEmail()));
        userRepository.findByEmail(user.getEmail()).ifPresentOrElse(
                existingUser -> {
                    System.out.println("User found");
                    ConfirmationToken confirmationToken = new ConfirmationToken(
                            generatedToken,
                            LocalDateTime.now(),
                            LocalDateTime.now().plusMinutes(15)
                    );
                    User user1 = (User) existingUser;
                    System.out.println(existingUser);
                    //userRepository.deleteById(user1.getUserId());
                    user1.setConfirmationToken(confirmationToken);
                    userRepository.save(user1);
                    //confirmationTokenRepository.save(confirmationToken);

                },
                () -> {
                    throw new IllegalStateException("User not found");
                }
        );
        return generatedToken;
    }

    private String generatreActivationCode(int lenght) {
        String chars ="0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i=0;i<lenght;i++) {
            int randomIndex = random.nextInt(chars.length());
            codeBuilder.append(chars.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Throwable {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    @Transactional
    public void activateAccount(String token) throws Throwable {

    }
}



