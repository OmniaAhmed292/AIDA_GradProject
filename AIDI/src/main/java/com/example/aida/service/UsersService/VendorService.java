package com.example.aida.service.UsersService;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.Vendor;
import com.example.aida.Entities.VendorSettings;
import com.example.aida.Repositories.VendorRepository;
import com.example.aida.auth.Authorization;
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

    @Autowired
    private Authorization authorization;


    public Vendor updateStoreInfo(String phoneNumber, Address address, String storeName){
        Vendor vendor = authorization.getVendorInfo();
        if (vendor != null) {
            vendor.setPhoneNumber(phoneNumber);
            vendor.setAddress(address);
            vendor.getBusinessInfo().setBusinessName(storeName);
            vendorRepository.save(vendor);
        }
        return vendor;
    }

    public Vendor updateCards(Set<Card> cards){
        Vendor vendor = authorization.getVendorInfo();
        if (vendor != null) {
            vendor.setCards(cards);
            vendorRepository.save(vendor);
        }
        return vendor;
    }

    public Vendor updateSettings(VendorSettings settings){
        Vendor vendor = authorization.getVendorInfo();
        if (vendor != null) {
            vendor.setSettings(settings);
            vendorRepository.save(vendor);
        }
        return vendor;
    }

    public Vendor getVendorInfo(){
        return authorization.getVendorInfo();
    }

}
