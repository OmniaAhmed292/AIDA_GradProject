package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class CartItem {
    @Id
    private String id;
    @Field(name ="productId",targetType = FieldType.OBJECT_ID)
    private String productId;
    @Field(name = "quantity")
    private Integer quantity;
    @Field(name = "name")
    private String name;
    @Field(name = "price")
    private Double price;
    @Field(name = "totalPrice")
    private Double totalPrice =0.0;
    @Field(name = "customerId")
    private String customerId;
    @Field(name = "updatedAt")
    private Date updatedAt;


}
