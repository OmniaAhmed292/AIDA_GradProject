package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag {

    @Id
    @Column(nullable = false, updatable = false, name = "tag_id")
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer tagId;

    @Column(nullable = false, length = 50, name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "productTagTags")
    private Set<Product> productTagProducts;

}
