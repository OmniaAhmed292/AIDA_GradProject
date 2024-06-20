package com.example.aida.auth;

import com.example.aida.Entities.Customer;
import com.example.aida.Entities.Vendor;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Authorization {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Customer getCustomerInfo(){
        String username = getAuthenticatedUsername();
        return customerRepository.findByEmail(username);
    }

    public Vendor getVendorInfo(){
        String username = getAuthenticatedUsername();
        return vendorRepository.findByEmail(username);
    }

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
