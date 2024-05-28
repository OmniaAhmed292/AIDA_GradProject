package com.example.aida.service.UsersService;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.Customer;
import com.example.aida.Entities.CustomerSettings;
import com.example.aida.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer updateInfo(Address address, String phone){
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        if (customer != null) {
            customer.setAddress(address);
            customer.setPhoneNumber(phone);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer updateCards(Set<Card> cards){
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        if (customer != null) {
            customer.setCards(cards);
        }
        return customer;
    }

    public Customer updateSettings(CustomerSettings settings){
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        if (customer != null) {
            customer.setCustomerSettings(settings);
        }
        return customer;
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
