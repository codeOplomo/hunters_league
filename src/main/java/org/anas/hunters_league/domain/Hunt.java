package org.anas.hunters_league.domain;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
public class Hunt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Species species;

    private Double weight;

    @ManyToOne
    private Participation participation;

    public Hunt() {
    }

    // Getter and Setter for id
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // Getter and Setter for species
    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    // Getter and Setter for weight
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    // Getter and Setter for participation
    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }
}


