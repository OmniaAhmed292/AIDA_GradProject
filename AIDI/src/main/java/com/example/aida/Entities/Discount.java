package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "discounts")
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_type", discriminatorType = DiscriminatorType.STRING)
public class Discount {
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
    private Integer discountId;

    @ColumnDefault("0")
    @Column(nullable = false, precision = 10, scale = 2,name = "percentage")
    private float percentage;

    @Column(nullable = true, length = 10,name = "Code")
    private String code;

    @Column(nullable = false, length = 10, name = "discount_type")
    private String type;





}

