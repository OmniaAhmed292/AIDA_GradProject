package com.example.aida.security;



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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.aida.Entities.User user = userRepository.findByEmail(username) // 1
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        //Opject represntaion of an Actual user
        return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

}
