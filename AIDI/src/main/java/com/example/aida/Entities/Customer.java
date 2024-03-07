package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
@DiscriminatorValue("customer")
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

    @Column
    private LocalDate birthdate;

    @Column
    private String gender;

    @Column
    private OffsetDateTime lastModifiedTime;

    @Column
    private Boolean settingsDeactivated;

    @Column
    private Boolean AllowEmailSubscribed;

    @Column
    private Boolean AllowEmailCartRecovery;

    @Column(name="points", nullable = false)
    @ColumnDefault("0")
    private int points;

    @Column(columnDefinition = "text")
    private String AllowInformationCollection;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", referencedColumnName = "user_id")
    private User customer;

    @OneToMany(mappedBy = "customer")
    private Set<Order> customerOrders;

    @OneToMany(mappedBy = "customer")
    private Set<StoreReview> customerStoreReviews;

    @OneToMany(mappedBy = "customer")
    private Set<ProductReview> customerProductReviews;

    @ManyToMany
    @JoinTable(
            name = "subscription",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> subscriptionProducts;



}
