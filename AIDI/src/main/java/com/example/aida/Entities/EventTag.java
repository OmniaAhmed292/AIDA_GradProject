package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Table(name = "event_tags")
@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTag {

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

    @Column(nullable = false, length = 50, name = "name")
    private String name;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, length = 100, name = "event_description")
    private String eventDescription;

    @Column(nullable = false,name = "image_name")
    private String imageName;

    @Column(nullable = false,name = "image_filepath")
    private String imageFilePath;



}
