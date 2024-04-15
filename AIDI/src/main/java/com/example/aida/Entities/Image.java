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
public class Image {
    @Field("file_path")
    private String filePath;
    @Field("file_name")
    private String fileName;
}