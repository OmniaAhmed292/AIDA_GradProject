package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductTags {

    @Field(name = "tag_id", targetType = FieldType.OBJECT_ID)
    private String tagId;

    @Field("tag_name")
    @Indexed(unique = true)
    private String tagName;

}
