package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private String orderId;

    @Field(name = "Shipment_price", targetType = FieldType.DECIMAL128)
    @NotNull
    private BigDecimal shipmentPrice;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name="address")
    @NotNull
    private Address address;

    @Embedded
    @Field(name = "order_items")
    private List<OrderItem> orderItems; // Embedded array of OrderItem

    @DBRef
    @Field(name = "customer_id")
    private Customer customer;

    @Field(name = "percentage_discount")
    @NotNull
    private Double pointDiscountPercentage;

    @Embedded
    @Field(name = "card") //TODO: add card to the order in mongoDB schema
    private Card card;

}
