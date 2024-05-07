package com.example.aida.Entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;
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
    private Date timeSinceRestocking;

    @Field(name="price")
    @NotNull
    private Double price;

    @Field(name = "taxes")
    @NotNull
    private Double taxes;

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
    private Date createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @Field(name="deleted_at")
    private Date deletedAt;

    @Embedded
    @Field(name = "images")
    private List<ProductImage> images;

    @Field(name = "tags")
    @Embedded
    private List<ProductTags> tags;

    @Field(name = "discount")
    @Embedded
    private Discount discount;

    @Field(name = "specifications")
    @Embedded
    private List<Specification> specifications;

    @Field(name = "reviews")
    @Embedded
    private List<Reviews> reviews;

    @Field(name = "vendor_id")
    private Vendor vendor;

    @Field(name = "shelf_id")
    private Shelf shelf;

    @Transient
    private Double rating;
}