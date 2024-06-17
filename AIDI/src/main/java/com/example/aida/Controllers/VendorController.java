package com.example.aida.Controllers;

import com.example.aida.Entities.*;
import com.example.aida.service.UsersService.CustomerService;
import com.example.aida.service.UsersService.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/vendor")
@RequiredArgsConstructor
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @GetMapping("/info")
    public ResponseEntity<Vendor> getVendorInfo(){
        return ResponseEntity.ok(vendorService.getVendorInfo());
    }

    @PostMapping("/update_info")
    public ResponseEntity<Vendor> updateInfo(@RequestBody InfoUpdateRequest infoUpdateRequest){
        return ResponseEntity.ok(vendorService.updateStoreInfo( infoUpdateRequest.getPhone(),infoUpdateRequest.getAddress(), infoUpdateRequest.getStoreName()));
    }

    @PostMapping("/update_cards")
    public ResponseEntity<Vendor> updateCards(@RequestBody Set<Card> cards){
        return ResponseEntity.ok(vendorService.updateCards(cards));
    }

    @PostMapping("/update_settings")
    public ResponseEntity<Vendor> updateSettings(@RequestBody VendorSettings settings){
        return ResponseEntity.ok(vendorService.updateSettings(settings));
    }
}
