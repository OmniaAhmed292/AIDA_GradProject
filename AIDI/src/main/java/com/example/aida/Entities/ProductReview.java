package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product_reviews")
public class ProductReview {

    @Id
    @Field(name = "review_id")
    private Integer reviewId;
    @Field(name = "body")
    private String body;
    @Field(name = "rate")
    private Integer rate;

   @Field(name = "date")
    private LocalDate date;

    @Field(name = "customer_id")
    private Customer customer;

    @Field(name = "product_id")
    private Product product;
}
