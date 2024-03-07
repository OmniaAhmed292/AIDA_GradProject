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
    @Column(nullable = false, updatable = false, name = "order_id")
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

    @Column(nullable = false, precision = 12, scale = 2, name = "Shipment_price")
    private BigDecimal shipmentPrice;

    @Column(nullable = false, name = "order_date")
    private LocalDate orderDate;

    @Column(nullable = false, length = 50,name="address_city")
    private String addressCity;

    @Column(nullable = false, length = 10,name="address_apartment_no")
    private String addressApartmentNo;

    @Column(nullable = false, length = 10,name="address_building_no")
    private String addressBuildingNo;

    @Column(nullable = false, length = 100,name="address_street")
    private String addressStreet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 10, name = "percentage_discount")
    private float pointDiscountPercentage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

}
