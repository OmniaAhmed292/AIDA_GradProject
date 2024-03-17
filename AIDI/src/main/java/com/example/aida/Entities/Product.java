package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @Field("product_id")
    private Integer productId;

    @Field(name = "product_name")
    @NotNull
    private String productName;

    @Field(name = "quantity")
    @NotNull
    private Integer quantity;


    @Field(name = "description")
    private String description;

    @Field(name = "is_used")
    @NotNull
    private Boolean isUsed;

    @Field(name="deletion_date")
    private LocalDate deletionDate;

    @Field(name="time_since_restocking")
    @NotNull
    private LocalDate timeSinceRestocking;

    @Field(name="price")
    @NotNull
    private BigDecimal price;

    @Field(name = "subscription_date")
    @NotNull
    private LocalDate subscriptionDate;

    @Field(name = "taxes")
    @NotNull
    private BigDecimal taxes;

    @Field(name="is_shown")
    @NotNull
    private Boolean isShown;

    @Field(name = "category_name")
    @NotNull
    private String categoryName;

    @Field(name = "purchases_no")
    @NotNull
    private Integer purchasesNo;

    @DBRef
    @Field(name = "shelf_id")
    private Shelf shelf;

    @Embedded
    @Field(name = "images")
    private Set<ProductImage> images;

    /*
    @OneToMany(mappedBy = "product")
    private Set<OrderItem> productOrderItems;


    @OneToMany(mappedBy = "product")
    private Set<ProductReview> productProductReviews;

    @ManyToMany(mappedBy = "subscriptionProducts")
    private Set<Customer> subscriptionCustomers;

     */

    @DBRef
    @Field(name = "tags")
    private Set<Tag> tags;


    @Field(name = "is_in_event")
    @NotNull
    private boolean isInEvent = false;

    @Field(name = "discount")
    @Embedded
    private Discount discount;

    @Field(name = "specifications")
    @Embedded
    private Set<Specification> specifications;

    @DBRef
    @Field(name = "vendor_id")
    private Vendor vendor;
}
