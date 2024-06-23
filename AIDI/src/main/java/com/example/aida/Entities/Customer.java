package com.example.aida.Entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @Field(name = "first_name")
    @NotNull

    private String fname;

    @Field(name = "last_name")
    @NotNull
    private String lname;


    @Field(name = "email")
    @NotNull
    @Indexed(unique = true)
    private String email;

    @Field(name = "hashed_password")
    @NotNull
    private String password;


    @Field(name = "birthdate")
    private LocalDate birthdate;

    @Field(name="gender")
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
    private String phoneNumber;

    @Field(name = "address")
    @Embedded
    private Address address;

    @Field(name = "balance")
    private Double balance;

    @Field(name = "cards")
    @Embedded
    private Set<Card> cards = new HashSet<>();

    @Field(name = "image")
    @Embedded
    private Image image;

    @Field(name = "subscriptions")
    @Embedded
    private Set<Subscription> subscriptions = new HashSet<>();

    @Field(name="cart")
    @Embedded
    private List<CartItem> cart = new ArrayList<>();

}
