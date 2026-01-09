package com.company.user_management.booking_domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "venues",
        indexes = {
                @Index(name = "idx_sport_code", columnList = "sport_code")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "sport_code", nullable = false)
    private String sportCode;
}
