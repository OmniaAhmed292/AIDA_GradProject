package com.example.aida.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @Column(nullable = false, updatable = false, name = "card_id")
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
    private Integer cardId;

    @Column(nullable = false, length = 20, name = "card_number")
    private String cardNumber;

    @Column(nullable = false,name="year")
    private Integer year;

    @Column(nullable = false,name="month")
    private Integer month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "card")
    private Order cardOrders;

}
