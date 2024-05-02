package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Dummy_Admin")
public class DummyAdmin {

    @Id
    private String id;

    @Field(name = "service_fees" , targetType = FieldType.DECIMAL128)
    private BigDecimal serviceFees;

    @Field(name = "points_to_discount_ratio", targetType = FieldType.DECIMAL128)
    private BigDecimal pointsToDiscountRatio;


    @Field(name = "Shipment_fees", targetType = FieldType.DECIMAL128)
    private BigDecimal shipmentFees;

    @Field(name = "Banner_price", targetType = FieldType.DECIMAL128)
    private BigDecimal bannerPrice;

    @Field(name = "bank_account")
    private String bankAccount;

    @Field(name = "Event_tag")
    @Embedded
    private EventTag eventTag;


}
