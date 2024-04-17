package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.Set;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tags")
public class Tag {

    @Id
    private String tagId;

    @Field("tag_name")
    @Indexed(unique = true)
    private String tagName;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
