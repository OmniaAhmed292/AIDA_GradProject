package com.example.aida.Entities;

import jakarta.persistence.*;
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
@Table(name = "vendors")
@DiscriminatorValue("vendor")
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


}
