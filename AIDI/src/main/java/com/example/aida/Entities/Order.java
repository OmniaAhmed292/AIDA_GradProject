package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

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
    private Integer orderId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal shipmentPrice;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false, length = 50)
    private String addressCity;

    @Column(nullable = false, length = 10)
    private String addressApartmentNo;

    @Column(nullable = false, length = 10)
    private String addressBuildingNo;

    @Column(nullable = false, length = 100)
    private String addressStreet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 10)
    private float pointDiscountPercentage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

}
