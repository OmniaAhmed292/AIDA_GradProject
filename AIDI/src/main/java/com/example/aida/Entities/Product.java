package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Entity
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

    @OneToMany(mappedBy = "pid")
    private Set<ProductEventTag> pidProductEventTags;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(final BigDecimal discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(final Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDate getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(final LocalDate deletionDate) {
        this.deletionDate = deletionDate;
    }

    public LocalDate getTimeSinceRestocking() {
        return timeSinceRestocking;
    }

    public void setTimeSinceRestocking(final LocalDate timeSinceRestocking) {
        this.timeSinceRestocking = timeSinceRestocking;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(final LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(final BigDecimal taxes) {
        this.taxes = taxes;
    }

    public Boolean getIsShown() {
        return isShown;
    }

    public void setIsShown(final Boolean isShown) {
        this.isShown = isShown;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getPurchasesNo() {
        return purchasesNo;
    }

    public void setPurchasesNo(final Integer purchasesNo) {
        this.purchasesNo = purchasesNo;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(final Shelf shelf) {
        this.shelf = shelf;
    }

    public Set<ProductImage> getProductProductImages() {
        return productProductImages;
    }

    public void setProductProductImages(final Set<ProductImage> productProductImages) {
        this.productProductImages = productProductImages;
    }

    public Set<OrderItem> getProductOrderItems() {
        return productOrderItems;
    }

    public void setProductOrderItems(final Set<OrderItem> productOrderItems) {
        this.productOrderItems = productOrderItems;
    }

    public Set<ProductReview> getProductProductReviews() {
        return productProductReviews;
    }

    public void setProductProductReviews(final Set<ProductReview> productProductReviews) {
        this.productProductReviews = productProductReviews;
    }

    public Set<Customer> getSubscriptionCustomers() {
        return subscriptionCustomers;
    }

    public void setSubscriptionCustomers(final Set<Customer> subscriptionCustomers) {
        this.subscriptionCustomers = subscriptionCustomers;
    }

    public Set<Tag> getProductTagTags() {
        return productTagTags;
    }

    public void setProductTagTags(final Set<Tag> productTagTags) {
        this.productTagTags = productTagTags;
    }

    public Set<ProductEventTag> getPidProductEventTags() {
        return pidProductEventTags;
    }

    public void setPidProductEventTags(final Set<ProductEventTag> pidProductEventTags) {
        this.pidProductEventTags = pidProductEventTags;
    }

}
