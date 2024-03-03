package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Entity
public class Vendor {

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

    @Column(columnDefinition = "text")
    private String aboutUsInfo;

    @Column(length = 50)
    private String businessType;

    @Column(length = 100)
    private String businessName;

    @Column
    private LocalDate expDay;

    @Column
    private String exoMonth;

    @Column
    private Integer settingsLateEmails;

    @Column
    private Integer settingsNewEmails;

    @Column
    private String applicationFilesPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @OneToMany(mappedBy = "vendor")
    private Set<Shelf> vendorShelves;

    @OneToMany(mappedBy = "vendor")
    private Set<StoreReview> vendorStoreReviews;

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

    public String getAboutUsInfo() {
        return aboutUsInfo;
    }

    public void setAboutUsInfo(final String aboutUsInfo) {
        this.aboutUsInfo = aboutUsInfo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(final String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(final String businessName) {
        this.businessName = businessName;
    }

    public LocalDate getExpDay() {
        return expDay;
    }

    public void setExpDay(final LocalDate expDay) {
        this.expDay = expDay;
    }

    public String getExoMonth() {
        return exoMonth;
    }

    public void setExoMonth(final String exoMonth) {
        this.exoMonth = exoMonth;
    }

    public Integer getSettingsLateEmails() {
        return settingsLateEmails;
    }

    public void setSettingsLateEmails(final Integer settingsLateEmails) {
        this.settingsLateEmails = settingsLateEmails;
    }

    public Integer getSettingsNewEmails() {
        return settingsNewEmails;
    }

    public void setSettingsNewEmails(final Integer settingsNewEmails) {
        this.settingsNewEmails = settingsNewEmails;
    }

    public String getApplicationFilesPath() {
        return applicationFilesPath;
    }

    public void setApplicationFilesPath(final String applicationFilesPath) {
        this.applicationFilesPath = applicationFilesPath;
    }

    public User getVendor() {
        return vendor;
    }

    public void setVendor(final User vendor) {
        this.vendor = vendor;
    }

    public Set<Shelf> getVendorShelves() {
        return vendorShelves;
    }

    public void setVendorShelves(final Set<Shelf> vendorShelves) {
        this.vendorShelves = vendorShelves;
    }

    public Set<StoreReview> getVendorStoreReviews() {
        return vendorStoreReviews;
    }

    public void setVendorStoreReviews(final Set<StoreReview> vendorStoreReviews) {
        this.vendorStoreReviews = vendorStoreReviews;
    }

}
