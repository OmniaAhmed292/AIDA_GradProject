package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Specification {
    @Field("attributeName")
    private String attributeName;

    @Field("attributeValue")
    private String attributeValue;

}
