package com.example.aida.Entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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
@Document(collection = "products")
public class Product {

    @Id
    private String _id;

    //--------------------
    // product details
    //--------------------
    @Field(name = "productName")
    @NotNull
    private String productName;

    @Field(name = "quantity")
    @NotNull
    private Integer quantity = 1;

    @Field(name = "description")
    private String description;

    @Field(name="timeSinceRestocking")
    @NotNull
    private LocalDate timeSinceRestocking;

    @Field(name="price")
    @NotNull
    private Double price;

    @Field(name = "taxes")
    @NotNull
    private double taxes;

    @Field(name = "categoryName")
    @NotNull
    private String categoryName;


    //--------------------
    // flags
    //--------------------

    @Field(name = "allowSubscription")
    @NotNull
    private Boolean allowSubscription;

    @Field(name = "isUsed")
    @NotNull
    private Boolean isUsed;

    @Field(name = "isInEvent")
    @NotNull
    private Boolean isInEvent = false;

    @Field(name="isShown")
    @NotNull
    private Boolean isShown;


    //--------------------
    // Timestamps
    //--------------------

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Field(name="deleted_at")
    private LocalDateTime deletedAt;

    //--------------------
    // statistics
    //--------------------

    @Field(name = "views")
    @NotNull
    private Integer views = 0;

    @Field(name = "sales")
    @NotNull
    private Integer sales = 0;

    @Field(name = "revenue")
    @NotNull
    private Double revenue = 0.0;

    @Field(name = "subscribers")
    @NotNull
    private Integer subscribers = 0;

    @Embedded
    @Field(name = "images")
    private Set<ProductImage> images = new HashSet<>();

    @Field(name = "tags")
    @Embedded
    private Set<ProductTags> tags = new HashSet<>();

    @Field(name = "discount")
    @Embedded
    private Discount discount;

    @Field(name = "specifications")
    private Set<Specification> specifications = new HashSet<>();

//    @Field(name = "reviews")
//    private Set<Reviews> reviews = new HashSet<>();

    @Field(name = "vendor_id", targetType = FieldType.OBJECT_ID)
    @Indexed(unique = false)
    private String vendorId;

    @Embedded
    @Field(name="reviews")
    private List<Reviews> reviewsList = new ArrayList<>();


    @Transient
    private Double rating;
}