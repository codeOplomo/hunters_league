package org.anas.hunters_league.domain;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hunt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Species species;

    private Double weight;

    @ManyToOne
    private Participation participation;

}


