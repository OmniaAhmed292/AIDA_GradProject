package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserImage {
    @Field("image_file_path")
    private String imageFilePath;

    @Field("image_file_name")
    private String imageFileName;
}
