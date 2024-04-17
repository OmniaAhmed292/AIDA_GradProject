package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BusinessInfo {
    @Field("about_us_info")
    private String aboutUsInfo;

    @Field("business_type")
    @NotNull
    private String businessType;

    @Field("business_name")
    @NotNull
    @Indexed(unique = false)
    private String businessName;

    @Field("exp_day")
    private LocalDate expDay;

    @Field("exp_month")
    private String expMonth;

    @Field(name = "is_verified")
    private boolean isVerified = true;
}
