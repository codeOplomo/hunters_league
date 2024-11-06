package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.anas.hunters_league.domain.enums.Difficulty;
import org.anas.hunters_league.domain.enums.SpeciesType;


public class SaveSpeciesVM {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Category is mandatory")
    private SpeciesType category;

    @Min(value = 0, message = "Minimum weight must be greater than or equal to 0")
    private Double minimumWeight;

    @NotNull(message = "Difficulty is mandatory")
    private Difficulty difficulty;

    @Min(value = 0, message = "Points must be greater than or equal to 0")
    private Integer points;

    // Getters and Setters
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

