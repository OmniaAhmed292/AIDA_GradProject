package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderItem {
    
    @Field(name = "quantity")
    @NotNull
    private Integer quantity;

    @Field(name = "status")
    @NotNull
    private String status;

    @Field(name = "Taxes")
    private Double taxes;

    @Field(name = "Product_price")
    private Double productPrice;

    @Field(name = "Discount_price")
    private Double discountPrice;

    @Field(name = "product_id")
    private ObjectId product;
}
