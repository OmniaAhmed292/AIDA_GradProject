package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.lang.annotation.Documented;
import java.util.Set;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tags")
public class Tag {
    @Id
    @Field(name = "tag_id", targetType = FieldType.OBJECT_ID)
    private Integer tagId;
    @Field("tag_name")
    private String tagName;
}
