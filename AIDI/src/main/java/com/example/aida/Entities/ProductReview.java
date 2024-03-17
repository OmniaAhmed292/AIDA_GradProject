package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product_reviews")
public class ProductReview {

    @Id
    @Field(name = "review_id", targetType = FieldType.OBJECT_ID)
    private String reviewId;
    @Field(name = "body")
    private String body;
    @Field(name = "rate")
    private Integer rate;

   @Field(name = "date")
    private LocalDate date;

    @Field(name = "customer_id")
    @DBRef
    private Customer customer;

    @Field(name = "product_id")
    @DBRef
    private Product product;
}
