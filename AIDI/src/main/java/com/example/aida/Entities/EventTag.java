package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Set;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EventTag {

    @Field(name = "name")
    private String name;

    @Field(name = "start_date")
    private LocalDate startDate;

    @Field(name = "end_date")
    private LocalDate endDate;

    @Field(name = "event_description")
    private String eventDescription;

    @Field(name = "image_name")
    private String imageName;

    @Field(name = "image_filepath")
    private String imageFilePath;

}
