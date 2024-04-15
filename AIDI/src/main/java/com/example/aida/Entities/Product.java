package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String productId;

    //--------------------
    // product details
    //--------------------
    @Field(name = "product_name")
    @NotNull
    private String productName;

    @Field(name = "quantity")
    @NotNull
    private Integer quantity = 1;

    @Field(name = "description")
    private String description;

    @Field(name="time_since_restocking")
    @NotNull
    private LocalDate timeSinceRestocking;

    @Field(name="price")
    @NotNull
    private BigDecimal price;

    @Field(name = "taxes")
    @NotNull
    private BigDecimal taxes;

    @Field(name = "category_name")
    @NotNull
    private String categoryName;


    //--------------------
    // flags
    //--------------------

    @Field(name = "is_used")
    @NotNull
    private Boolean isUsed;

    @Field(name = "is_in_event")
    @NotNull
    private boolean isInEvent = false;

    @Field(name="is_shown")
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

    @Embedded
    @Field(name = "images")
    private Set<ProductImage> images;

    @Field(name = "tags")
    @Embedded
    private Set<ProductTags> tags;

    @Field(name = "discount")
    @Embedded
    private Discount discount;

    @Field(name = "specifications")
    @Embedded
    private Set<Specification> specifications;

    @Field(name = "reviews")
    @Embedded
    private Set<Reviews> reviews;

    @Field(name = "vendor_id")
    private Vendor vendor;

    @Field(name = "shelf_id")
    private Shelf shelf;
}