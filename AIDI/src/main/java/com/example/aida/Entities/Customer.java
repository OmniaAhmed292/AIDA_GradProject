package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @Field(name = "first_name")
    @NotNull
    private String firstName;

    @Field(name = "last_name")
    @NotNull
    private String lastName;

    @Field(name = "email")
    @NotNull
    @Indexed(unique = true)
    private String email;

    @Field(name = "hashed_password")
    @NotNull
    private String hashedPassword;

    @Field(name = "birthdate")
    private LocalDate birthdate;

    @Field
    private String gender;

    @Field(name="points")
    @NotNull
    private int points = 0;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Field(name = "settings")
    @Embedded
    private CustomerSettings customerSettings;

    @Field(name = "phone_number")
    private int phoneNumber;

    @Field(name = "address")
    @Embedded
    private Address address;

    @Field(name = "balance")
    private BigDecimal balance;

    @Field(name = "cards")
    @Embedded
    private Set<Card> cards;

    @Field(name = "image")
    @Embedded
    private Image image;

}
