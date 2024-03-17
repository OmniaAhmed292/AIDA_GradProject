package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Data
@EqualsAndHashCode(callSuper = false) //TODO: add them for other inheritence classes
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("number_limited")
@Embeddable
public class NumberLimitedDiscount extends Discount{
    @Field(name = "max_purchase")
    @NotNull
    private Integer maxPurchase;

    @Field(name = "current_purchase")
    @NotNull
    private Integer currentPurchase;
}
