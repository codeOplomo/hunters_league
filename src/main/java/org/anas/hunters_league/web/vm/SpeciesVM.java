package org.anas.hunters_league.web.vm;

import org.anas.hunters_league.domain.enums.Difficulty;
import org.anas.hunters_league.domain.enums.SpeciesType;

import java.util.UUID;

public class SpeciesVM {
    private UUID id;
    private String name;
    private SpeciesType category;
    private Double minimumWeight;
    private Difficulty difficulty;
    private Integer points;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpeciesType getCategory() {
        return category;
    }

    public void setCategory(SpeciesType category) {
        this.category = category;
    }

    public Double getMinimumWeight() {
        return minimumWeight;
    }

    public void setMinimumWeight(Double minimumWeight) {
        this.minimumWeight = minimumWeight;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}


