package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {

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
    private Long id;

    @Column(precision = 12, scale = 2)
    private BigDecimal balance;

    @Column
    private LocalDate birthdate;

    @Column
    private String gender;

    @Column
    private OffsetDateTime lastModifiedTime;

    @Column
    private Boolean settingsDeactivated;

    @Column
    private Boolean settingsEmailSubscribed;

    @Column
    private Boolean settingsEmailCartRecovery;

    @Column(columnDefinition = "text")
    private String settingsCollectInformation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "customer")
    private Set<Order> customerOrders;

    @OneToMany(mappedBy = "customer")
    private Set<StoreReview> customerStoreReviews;

    @OneToMany(mappedBy = "customer")
    private Set<ProductReview> customerProductReviews;

    @ManyToMany
    @JoinTable(
            name = "Subscription",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private Set<Product> subscriptionProducts;



}
