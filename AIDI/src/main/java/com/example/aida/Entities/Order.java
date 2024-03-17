package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    @Field(name = "order_id")
    private Integer orderId;

    @Field(name = "Shipment_price")
    @NotNull
    private BigDecimal shipmentPrice;

    @Field(name = "order_date")
    @NotNull
    private LocalDate orderDate;

    @Field(name="address_city")
    @NotNull
    private String addressCity;

    @Field(name="address_apartment_no")
    @NotNull
    private String addressApartmentNo;

    @Field(name="address_building_no")
    @NotNull
    private String addressBuildingNo;

    @Field(name="address_street")
    @NotNull
    private String addressStreet;

    @Embedded
    @Field(name = "order_items")
    private List<OrderItem> orderItems; // Embedded array of OrderItem

    @DBRef
    @Field(name = "customer_id")
    private Customer customer;

    @Field(name = "percentage_discount")
    @NotNull
    private float pointDiscountPercentage;

    @Embedded
    @Field(name = "card") //TODO: add card to the order in mongoDB schema
    private Card card;

}
