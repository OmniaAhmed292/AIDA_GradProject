package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Specification {
    @Field("attributeName")
    private String attributeName;

    @Field("attributeValue")
    private String attributeValue;

}
