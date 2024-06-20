package com.example.aida.Entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String _id;

    @Field(name = "shipment_price")
    @NotNull
    private Double shipmentPrice;

    @Field(name = "order_date")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name="address")
    @NotNull
    private Address address;


    @Embedded
    @Field(name = "order_items")
    private List<OrderItem> orderItems; // Embedded array of OrderItem


    @Field(name = "customer_id", targetType = FieldType.OBJECT_ID)
    private String customer;

    @Field(name = "percentage_discount")
    @NotNull
    private Double pointDiscountPercentage;

    @Embedded
    @Field(name = "card") //TODO: add card to the order in mongoDB schema
    private Card card;

}
