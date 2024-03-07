package com.example.aida.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("number_limited")
public class NumberLimitedDiscount extends Discount{
    private Integer maxPurchase;
    private Integer currentPurchase;
}
