package com.example.aida.Entities;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Embeddable
public class Specification {
    @Field("attribute_name")
    private String attributeName;

    @Field("attribute_value")
    private String attributeValue;

}
