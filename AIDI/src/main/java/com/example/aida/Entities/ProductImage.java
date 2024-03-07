package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_images")
public class ProductImage {

    @Id
    @Column(nullable = false, updatable = false, name = "image_id")
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

    @Column(nullable = false, length = 50, name = "image_name")
    private String imageName;

    @Column(nullable = false, columnDefinition = "text", name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = true, columnDefinition = "text", name = "image_description")
    private String Description;
}
