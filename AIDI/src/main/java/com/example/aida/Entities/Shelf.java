package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Set;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Shelf {
    @Id
    @Field(name = "shelf_id", targetType = FieldType.OBJECT_ID)
    private String shelfId;
    @Field("shelf_name")
    private String shelfName;
}
