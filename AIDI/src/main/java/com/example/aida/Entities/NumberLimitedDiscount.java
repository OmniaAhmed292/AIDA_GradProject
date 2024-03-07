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
@DiscriminatorValue("number_limited")
public class NumberLimitedDiscount extends Discount{
    @Column(nullable = false, name = "max_purchase")
    private Integer maxPurchase;
    @Column(nullable = false, name = "current_purchase")
    private Integer currentPurchase;
}
