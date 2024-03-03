package com.example.aida.Services.security;
import com.example.aida.Repositories.UserRepository;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/* Need Update From US */

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        com.example.aida.Entities.User user = userRepository.findByEmail(Email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + Email + " not found"));
        //Opject represntaion of an Actual user
        return new User(user.getEmail(), user.getHashedPassword(), user.getAuthorities());
    }

}
