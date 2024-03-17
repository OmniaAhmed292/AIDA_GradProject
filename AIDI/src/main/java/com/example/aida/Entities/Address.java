package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.mongodb.core.mapping.Field;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @Field(name = "address_street")
    private String street;

    @Field(name = "address_city")
    private String city;

    @Field(name = "address_apartment_no")
    private String apartmentNo;

    @Field(name = "address_building_no")
    private String BuildingNo;
}
