package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummyAdmin {

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
    private BigDecimal serviceFees;

    @Column(precision = 7, scale = 2)
    private BigDecimal pointsToDiscountRatio;

    @Column(precision = 10, scale = 2)
    private BigDecimal shipmentFees;

    @Column(precision = 14, scale = 2)
    private BigDecimal bannerPrice;


    @Column(length = 100)
    private String bankAcount;


}
