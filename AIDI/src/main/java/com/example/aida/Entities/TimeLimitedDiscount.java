package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("time_limited")
public class TimeLimitedDiscount extends Discount{
    @Column(name = "EndDate")
    private String endDate;
}
