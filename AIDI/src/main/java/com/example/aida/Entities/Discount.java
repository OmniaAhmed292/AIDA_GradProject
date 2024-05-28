package com.example.aida.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;


@Embeddable
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    /**
     * The percentage of the discount
     * 0% by default
     * 25.5 for 25.5%
     */
    @Field(name = "percentage")
    @NotNull
    private Double percentage = 0.0;

    /**
     * The amount of the discount
     */
    @Field(name = "Code")
    private String code;


    @Field(name = "end_date")
    private LocalDate endDate;
}

