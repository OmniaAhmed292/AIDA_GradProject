package com.example.aida.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer productId;

    @Column(nullable = false, length = 50)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 12, scale = 2)
    private BigDecimal discount;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private Boolean isUsed;

    @Column
    private LocalDate deletionDate;

    @Column(nullable = false)
    private LocalDate timeSinceRestocking;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate subscriptionDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal taxes;

    @Column(nullable = false)
    private Boolean isShown;

    @Column(nullable = false, length = 50)
    private String categoryName;

    @Column(nullable = false)
    private Integer purchasesNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productProductImages;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> productOrderItems;

    @OneToMany(mappedBy = "product")
    private Set<ProductReview> productProductReviews;

    @ManyToMany(mappedBy = "subscriptionProducts")
    private Set<Customer> subscriptionCustomers;

    @ManyToMany
    @JoinTable(
            name = "ProductTag",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private Set<Tag> productTagTags;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isInEvent;

    @Column(nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discountID;



}
