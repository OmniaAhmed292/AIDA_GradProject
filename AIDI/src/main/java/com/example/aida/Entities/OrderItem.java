package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;


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
    private BigDecimal taxes;

    @Field(name = "Product_price")
    private BigDecimal productPrice;

    @Field(name = "Discount_price")
    private BigDecimal discountPrice;

    @DBRef
    @Field(name = "product_id")
    private Product product;
}
