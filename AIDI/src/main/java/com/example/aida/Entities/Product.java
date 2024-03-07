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
@Table(name = "products")
public class Product {

    @Id
    @Column(nullable = false, updatable = false, name = "product_id")
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

    @Column(nullable = false, length = 50, name = "product_name")
    private String productName;

    @Column(nullable = false,name = "quantity")
    private Integer quantity;

    @Column(precision = 12, scale = 2, name="discount")
    private BigDecimal discount;

    @Column(length = 100, name = "description")
    private String description;

    @Column(nullable = false,name = "is_used")
    private Boolean isUsed;

    @Column(name="deletion_date")
    private LocalDate deletionDate;

    @Column(nullable = false,name="time_since_restocking")
    private LocalDate timeSinceRestocking;

    @Column(nullable = false, precision = 12, scale = 2,name="price")
    private BigDecimal price;

    @Column(nullable = false,name = "subscription_date")
    private LocalDate subscriptionDate;

    @Column(nullable = false, precision = 12, scale = 2,name = "taxes")
    private BigDecimal taxes;

    @Column(nullable = false,name="is_shown")
    private Boolean isShown;

    @Column(nullable = false, length = 50, name = "category_name")
    private String categoryName;

    @Column(nullable = false, name = "purchases_no")
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
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name ="tag_id" )
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
