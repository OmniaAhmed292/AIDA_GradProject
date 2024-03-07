package com.example.aida.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.*;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEventTag {

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
    private Integer imageId;

    @Column(nullable = false, length = 50)
    private String imageName;

    @Column(nullable = false, columnDefinition = "text")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid_id", nullable = false)
    private Product pid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid_id", nullable = false)
    private EventTag eid;

}
