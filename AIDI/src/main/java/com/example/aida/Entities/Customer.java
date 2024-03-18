package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Document(collection = "customers")
@Setter
@Getter
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TypeAlias("customer")
public class Customer extends User {

    @Id
    @Field(name = "customer_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(name = "birthdate")
    private LocalDate birthdate;

    @Field
    private String gender;

    @Field
    private OffsetDateTime lastModifiedTime;

    @Field(name = "Settings")
    @Embedded
    private Settings settings;

    @Field(name="points")
    @NotNull
    private int points = 0;

/*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", referencedColumnName = "user_id")
    private User customer;


    @DBRef(lazy = true)
    @Field(name = "orders")
    private Set<Order> customerOrders;

    @DBRef(lazy = true)
    private Set<StoreReview> customerStoreReviews;

    @DBRef(lazy = true)
    private Set<ProductReview> customerProductReviews;



    //TODO: fix annotation to fit mongodb
    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> subscriptionProducts;
    */

}
