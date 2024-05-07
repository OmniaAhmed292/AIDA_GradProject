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

import java.time.LocalDateTime;
import java.util.Set;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "vendors")
public class Vendor{
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

    @Field(name = "business_info")
    private BusinessInfo businessInfo;

    @Field(name = "phone_number")
    private String phoneNumber;

    @Field(name = "address")
    @Embedded
    private Address address;

    @Field(name = "balance")
    private Double balance =0.0;

    @Field(name = "cards")
    @Embedded
    private Set<Card> cards;

    @Field(name = "image")
    @Embedded
    private Image image;

    @Field(name = "settings")
    @Embedded
    private VendorSettings settings;


    @Embedded
    @Field("shelves")
    private Set<Shelf> vendorShelves;

    @Embedded
    @Field("reviews")
    private Set<Reviews> vendorReviews;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
