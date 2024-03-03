package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "\"Order\"")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(final Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getShipmentPrice() {
        return shipmentPrice;
    }

    public void setShipmentPrice(final BigDecimal shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(final String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressApartmentNo() {
        return addressApartmentNo;
    }

    public void setAddressApartmentNo(final String addressApartmentNo) {
        this.addressApartmentNo = addressApartmentNo;
    }

    public String getAddressBuildingNo() {
        return addressBuildingNo;
    }

    public void setAddressBuildingNo(final String addressBuildingNo) {
        this.addressBuildingNo = addressBuildingNo;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(final String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(final Card card) {
        this.card = card;
    }

}
