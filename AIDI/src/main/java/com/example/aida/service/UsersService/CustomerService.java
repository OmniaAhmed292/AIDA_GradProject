package com.example.aida.service.UsersService;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.Customer;
import com.example.aida.Entities.CustomerSettings;
import com.example.aida.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerInfo(){
        String username = getAuthenticatedUsername();
        return customerRepository.findByEmail(username);
    }

    public Customer updateInfo(Address address, String phone){
        Customer customer = getCustomerInfo();
        if (customer != null) {
            customer.setAddress(address);
            customer.setPhoneNumber(phone);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer updateCards(Set<Card> cards){
        Customer customer = getCustomerInfo();
        if (customer != null) {
            customer.setCards(cards);
        }
        return customer;
    }

    public Customer updateSettings(CustomerSettings settings){
        Customer customer = getCustomerInfo();
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
