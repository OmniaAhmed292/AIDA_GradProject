package com.example.aida;

import com.example.aida.Entities.User;
import com.example.aida.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AidAApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidAApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(UserRepository userRepository) {
        return args -> {
            User user = new User();
            user.setFname("John");
            user.setLname("Doe");
            user.setEmail("johndoe@gmail.com");
            user.setPassword("password");
            user.setUserType("vendor");
            userRepository.insert(user);
        };
    }
}
