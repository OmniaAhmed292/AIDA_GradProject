package com.example.aida.auth;

import com.example.aida.Entities.*;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.UserRepository;
import com.example.aida.Repositories.VendorRepository;
import com.example.aida.config.JwtService;
import com.example.aida.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@DependsOn("envConfig")
public class AuthenticationService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailValidator emailValidator;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailServiceImpl;

    @Value("#{systemProperties['TOKEN_LENGTH']}")
    private int tokenLength;

    //    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {
        //System.out.println(request);
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
                .userType(request.getUserType())
                .isEnabled(false)
                .isAccountLocked(false)
                .confirmationToken(null)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("User already exists");
            throw new IllegalStateException("User already exists");
        }

        //-----------------------------
        //  Customer Creation
        //-----------------------------
        if(request.getUserType().equals("customer")){
            var customerBuilder = Customer.builder()
                .fname(request.getFname())
                .lname(request.getLname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .customerSettings(new CustomerSettings(true, true, true, true))
                .balance(0.0)
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
        else if(request.getUserType().equals("vendor")) {
            var vendorBuilder = Vendor.builder()
                    .fname(request.getFname())
                    .lname(request.getLname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .balance(0.0)
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
        //System.out.println(user);
        String emailToken = generateActivationCode(user.getEmail(),tokenLength);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                emailToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );
        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);
        SendValidationEmail(user, emailToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void SendValidationEmail(User user, String token) throws MessagingException {
        String activationLink = "http://localhost:8080/api/v1/auth/activate-account/" + token; // replace with your server URL and endpoint
//        send email
        emailServiceImpl.sendMail(
                user.getEmail(),
                "Activate your account",
                "Hello "+user.getFname()+" "+user.getLname()+"\n"+
                        "Thank you for signing up! Please use the following activation code to activate your account: \n"+activationLink
                );
    }


    private String generateActivationCode(String email,int length) {
        String chars ="0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        String uniqueEmail = email + System.currentTimeMillis();
        // append current time in milliseconds to the email, since the email is unique the token is unique,
        // and the random part (currentTime) ensures that the token is not predictable and varies each time
        long seed = uniqueEmail.hashCode(); // use the uniqueEmail's hashcode as a seed

        SecureRandom random = new SecureRandom();
        random.setSeed(seed);
        for(int i=0;i<length;i++) {
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

    public void activateAccount(String token) throws IllegalStateException {

        User user = userRepository.findByConfirmationTokenToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        ConfirmationToken confirmationToken = user.getConfirmationToken();
        if(confirmationToken.getConfirmedDate() != null) {
            throw new IllegalStateException("Email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiredDate();
        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        confirmationToken.setConfirmedDate(LocalDateTime.now());

        user.setIsEnabled(true);
        userRepository.save(user);
    }
}



