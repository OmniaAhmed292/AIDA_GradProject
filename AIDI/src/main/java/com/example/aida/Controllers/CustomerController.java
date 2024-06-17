package com.example.aida.Controllers;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.Customer;
import com.example.aida.Entities.CustomerSettings;
import com.example.aida.service.UsersService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/info")
    public ResponseEntity<Customer> getCustomerInfo(){
        return ResponseEntity.ok(customerService.getCustomerInfo());
    }

    @PostMapping("/update_info")
    public ResponseEntity<Customer> updateInfo(@RequestBody InfoUpdateRequest infoUpdateRequest){
        return ResponseEntity.ok(customerService.updateInfo(infoUpdateRequest.getAddress(), infoUpdateRequest.getPhone()));
    }

    @PostMapping("/update_cards")
    public ResponseEntity<Customer> updateCards(@RequestBody Set<Card> cards){
        return ResponseEntity.ok(customerService.updateCards(cards));
    }

    @PostMapping("/update_settings")
    public ResponseEntity<Customer> updateSettings(@RequestBody CustomerSettings settings){
        return ResponseEntity.ok(customerService.updateSettings(settings));
    }

}
