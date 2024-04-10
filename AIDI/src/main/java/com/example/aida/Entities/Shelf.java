package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.Set;



@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Shelf {

    @Field(name = "shelf_name")
    @Indexed(unique = true)
    private String shelfName;

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @Field(name = "productIds")
    private Set<String> productIds;

}
