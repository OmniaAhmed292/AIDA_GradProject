package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Shelf {
    @Id
    @Field("shelf_id")
    private Integer shelfId;
    @Field("shelf_name")
    private String shelfName;
}
