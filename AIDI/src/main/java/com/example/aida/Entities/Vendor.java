package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Vendor extends User {
   @Id
    private Long id;
    @Field("about_us_info")
    private String aboutUsInfo;

    @Field("business_type")
    private String businessType;

    @Field("business_name")
    private String businessName;

   @Field("exp_day")
    private LocalDate expDay;

    @Field("exp_month")
    private String exoMonth;


    @Embedded
    @Field("vendor_settings")
    private VendorSettings vendorSettings;

    @Field("application_files_path")
    private String applicationFilesPath;


    @Embedded
    @Field("shelves")
    private Set<Shelf> vendorShelves;

    @Embedded
    @Field("reviews")
    private Set<StoreReview> vendorStoreReviews;


}
