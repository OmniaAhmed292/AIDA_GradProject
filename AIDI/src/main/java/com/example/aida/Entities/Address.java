package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @Column(length = 100, name = "address_street")
    private String street;

    @Column(length = 100, name = "address_city")
    private String city;

    @Column(length = 10, name = "address_apartment_no")
    private String apartmentNo;

    @Column(length = 10, name = "address_building_no")
    private String BuildingNo;
}
