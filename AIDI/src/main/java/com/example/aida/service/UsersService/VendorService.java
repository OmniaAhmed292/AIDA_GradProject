package com.example.aida.service.UsersService;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.Vendor;
import com.example.aida.Entities.VendorSettings;
import com.example.aida.Repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public Vendor getVendorInfo(){
        String username = getAuthenticatedUsername();
        return vendorRepository.findByEmail(username);
    }

    public Vendor updateStoreInfo(String phoneNumber, Address address, String storeName){
        Vendor vendor = getVendorInfo();
        if (vendor != null) {
            vendor.setPhoneNumber(phoneNumber);
            vendor.setAddress(address);
            vendor.getBusinessInfo().setBusinessName(storeName);
            vendorRepository.save(vendor);
        }
        return vendor;
    }

    public Vendor updateCards(Set<Card> cards){
        Vendor vendor = getVendorInfo();
        if (vendor != null) {
            vendor.setCards(cards);
            vendorRepository.save(vendor);
        }
        return vendor;
    }

    public Vendor updateSettings(VendorSettings settings){
        Vendor vendor = getVendorInfo();
        if (vendor != null) {
            vendor.setSettings(settings);
            vendorRepository.save(vendor);
        }
        return vendor;
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
