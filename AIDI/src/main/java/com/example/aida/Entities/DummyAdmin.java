package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Dummy_Admin")
public class DummyAdmin {

    @Id
    @Field(name = "Dummy_Admin_id")
    private Long id;

    @Field(name = "service_fees")
    private BigDecimal serviceFees;

    @Field(name = "points_to_discount_ratio")
    private BigDecimal pointsToDiscountRatio;


    @Field(name = "Shipment_fees")
    private BigDecimal shipmentFees;

    @Field(name = "Banner_price")
    private BigDecimal bannerPrice;

    //TODO: fix bank account typo
    @Field(name = "bank_acount")
    private String bankAccount;

    @Field(name = "Event_tag")
    @Embedded
    private EventTag eventTag;


}
