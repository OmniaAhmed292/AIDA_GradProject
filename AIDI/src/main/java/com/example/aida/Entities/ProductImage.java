package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductImage {
    @Id
    String imageId;
    @Field("image_name")
    private String imageName;

    @Field("file_path")
    private String ImageUrl;

    @Field("description")
    private String Description;
}
