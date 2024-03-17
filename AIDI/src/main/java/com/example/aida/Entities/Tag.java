package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.lang.annotation.Documented;
import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tags")
public class Tag {
    @Id
    @Field("tag_id")
    private Integer tagId;
    @Field("tag_name")
    private String tagName;
}
