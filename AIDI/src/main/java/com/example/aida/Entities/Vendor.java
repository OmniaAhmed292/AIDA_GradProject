package com.example.aida.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "vendors")
public class Vendor extends User {

    @Id
    @Column(nullable = false, updatable = false, name = "vendor_id")
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

    @Column(precision = 12, scale = 2, name = "balance")
    private BigDecimal balance;

    @Column(columnDefinition = "text", name = "About_us_info")
    private String aboutUsInfo;

    @Column(length = 50, name = "business_type")
    private String businessType;

    @Column(length = 100, name = "business_name")
    private String businessName;

    @Column(name = "exp_day")
    private LocalDate expDay;

    @Column(name="exo_month")
    private String exoMonth;

    @Column(name = "settings_late_emails")
    private Integer settingsLateEmails;

    @Column(name = "settings_new_emails")
    private Integer settingsNewEmails;

    @Column(name = "application_files_path")
    private String applicationFilesPath;

    @OneToOne(fetch = FetchType.LAZY)
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
