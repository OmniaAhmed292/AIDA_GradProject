package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Card {
    @Field(name = "card_number")
    @NotNull
    private String cardNumber;

    @Field(name="year")
    @NotNull
    private Integer year;

    @Field(name="month")
    @NotNull
    private Integer month;
}
