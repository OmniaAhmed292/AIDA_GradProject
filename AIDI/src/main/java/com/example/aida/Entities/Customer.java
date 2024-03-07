package com.example.aida.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public OffsetDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(final OffsetDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Boolean getSettingsDeactivated() {
        return settingsDeactivated;
    }

    public void setSettingsDeactivated(final Boolean settingsDeactivated) {
        this.settingsDeactivated = settingsDeactivated;
    }

    public Boolean getSettingsEmailSubscribed() {
        return settingsEmailSubscribed;
    }

    public void setSettingsEmailSubscribed(final Boolean settingsEmailSubscribed) {
        this.settingsEmailSubscribed = settingsEmailSubscribed;
    }

    public Boolean getSettingsEmailCartRecovery() {
        return settingsEmailCartRecovery;
    }

    public void setSettingsEmailCartRecovery(final Boolean settingsEmailCartRecovery) {
        this.settingsEmailCartRecovery = settingsEmailCartRecovery;
    }

    public String getSettingsCollectInformation() {
        return settingsCollectInformation;
    }

    public void setSettingsCollectInformation(final String settingsCollectInformation) {
        this.settingsCollectInformation = settingsCollectInformation;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(final User customer) {
        this.customer = customer;
    }

    public Set<Order> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(final Set<Order> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public Set<StoreReview> getCustomerStoreReviews() {
        return customerStoreReviews;
    }

    public void setCustomerStoreReviews(final Set<StoreReview> customerStoreReviews) {
        this.customerStoreReviews = customerStoreReviews;
    }

    public Set<ProductReview> getCustomerProductReviews() {
        return customerProductReviews;
    }

    public void setCustomerProductReviews(final Set<ProductReview> customerProductReviews) {
        this.customerProductReviews = customerProductReviews;
    }

    public Set<Product> getSubscriptionProducts() {
        return subscriptionProducts;
    }

    public void setSubscriptionProducts(final Set<Product> subscriptionProducts) {
        this.subscriptionProducts = subscriptionProducts;
    }

}
