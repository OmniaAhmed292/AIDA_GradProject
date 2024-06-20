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

    @Field("imageName")
    private String imageName;

    @Field("filePath")
    private String filePath;

    @Field("imageDescription")
    private String Description;

}
