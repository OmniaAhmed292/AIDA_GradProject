package com.example.aida.Controllers;


import com.example.aida.Entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InfoUpdateRequest {
    private Address address;
    private String phone;
    private String storeName;
}
