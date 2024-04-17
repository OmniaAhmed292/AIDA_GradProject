package com.example.aida;


import com.example.aida.Repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class AidAApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidAApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(VendorRepository userRepository) {
        return args -> {
//            User user = new User();
//            user.setFname("John");
//            user.setLname("Doe");
//            user.setEmail("johndoe@gmail.com");
//            user.setHashedPassword("password");
//            user.setUserType("vendor");
//            userRepository.insert(user);
        };
    }
}
