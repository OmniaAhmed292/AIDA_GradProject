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
@Table(name = "dummy_admin")
public class DummyAdmin {

    @Id
    @Column(nullable = false, updatable = false,name="Dummy_Admin_id")
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

    @Column(precision = 12, scale = 2, name = "service_fees")
    private BigDecimal serviceFees;

    @Column(precision = 7, scale = 2, name = "points_to_discount_ratio")
    private BigDecimal pointsToDiscountRatio;

    @Column(precision = 10, scale = 2,name="Shipment_fees")
    private BigDecimal shipmentFees;

    @Column(precision = 14, scale = 2,name = "Banner_price")
    private BigDecimal bannerPrice;


    @Column(length = 100,name = "bank_acount")
    private String bankAccount;


}
