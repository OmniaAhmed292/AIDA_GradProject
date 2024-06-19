package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderItem {

    @Id
    private String _id;
    
    @Field(name = "quantity")
    @NotNull
    private Integer quantity;

    @Field(name = "Status")
    @NotNull
    private String status;

    @Field(name = "Taxes")
    private Double taxes;

    @Field(name = "Product_price")
    private Double productPrice;

    @Field(name = "Discount_price")
    private Double discountPrice;

    @DBRef
    @Field(name = "product_id", targetType = FieldType.OBJECT_ID)
    private String productId;

    @Field(name = "vendor_id", targetType = FieldType.OBJECT_ID)
    private String vendorId;
}
