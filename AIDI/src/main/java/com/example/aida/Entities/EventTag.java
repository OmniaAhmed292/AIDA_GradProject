package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTag {

    @Id
    @Column(nullable = false, updatable = false)
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

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String eventDescription;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imageFilePath;



}
