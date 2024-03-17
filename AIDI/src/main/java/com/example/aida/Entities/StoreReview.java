package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StoreReview {

    @Field("body")
    private String body;
    @Field("rate")
    private Integer rate;
    @Field("date")
    private LocalDate date;

    //Need to be reviewed
   @Field("customer_id")
   private Customer customer;

}
