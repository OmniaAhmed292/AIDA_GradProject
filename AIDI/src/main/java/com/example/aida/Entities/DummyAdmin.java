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
import java.math.BigDecimal;


@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dummy_admin_id")
    private User dummyAdmin;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(final BigDecimal serviceFees) {
        this.serviceFees = serviceFees;
    }

    public BigDecimal getPointsToDiscountRatio() {
        return pointsToDiscountRatio;
    }

    public void setPointsToDiscountRatio(final BigDecimal pointsToDiscountRatio) {
        this.pointsToDiscountRatio = pointsToDiscountRatio;
    }

    public BigDecimal getShipmentFees() {
        return shipmentFees;
    }

    public void setShipmentFees(final BigDecimal shipmentFees) {
        this.shipmentFees = shipmentFees;
    }

    public BigDecimal getBannerPrice() {
        return bannerPrice;
    }

    public void setBannerPrice(final BigDecimal bannerPrice) {
        this.bannerPrice = bannerPrice;
    }

    public User getDummyAdmin() {
        return dummyAdmin;
    }

    public void setDummyAdmin(final User dummyAdmin) {
        this.dummyAdmin = dummyAdmin;
    }

}
