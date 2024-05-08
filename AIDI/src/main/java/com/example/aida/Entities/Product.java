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

import java.math.BigDecimal;
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
    private String productId;

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

    @Field(name="time_since_restocking")
    @NotNull
    private LocalDate timeSinceRestocking;

    @Field(name="price", targetType = FieldType.DECIMAL128)
    @NotNull
    private BigDecimal price;

    @Field(name = "taxes", targetType = FieldType.DECIMAL128)
    @NotNull
    private BigDecimal taxes;

    @Field(name = "categoryName")
    @NotNull
    private String categoryName;


    //--------------------
    // flags
    //--------------------

    @Field(name = "isUsed")
    @NotNull
    private Boolean isUsed;

    @Field(name = "is_in_event")
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
    @Embedded
    private Set<Specification> specifications = new HashSet<>();

    @Field(name = "reviews")
    private Set<Reviews> reviews = new HashSet<>();

    @Field(name = "vendor_id", targetType = FieldType.OBJECT_ID)
    @Indexed(unique = false)
    private String vendorId;


    @Transient
    private Double rating;
}