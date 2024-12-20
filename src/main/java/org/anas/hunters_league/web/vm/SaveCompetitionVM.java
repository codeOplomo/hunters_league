package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.*;
import org.anas.hunters_league.domain.enums.SpeciesType;

import java.time.LocalDateTime;


public class SaveCompetitionVM {

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @Future(message = "Date must be in the future")
    private LocalDateTime date;

    @NotNull(message = "Species Type is required")
    private SpeciesType speciesType;

    @NotNull(message = "Minimum participants is required")
    @Min(value = 1, message = "Minimum participants must be at least 1")
    private Integer minParticipants;

    @NotNull(message = "Maximum participants is required")
    @Min(value = 2, message = "Maximum participants must be at least 2")
    private Integer maxParticipants;

    @AssertTrue(message = "Maximum participants must be greater than minimum participants")
    public boolean isMaxParticipantsGreaterThanMinParticipants() {
        return maxParticipants != null && minParticipants != null && maxParticipants > minParticipants;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(SpeciesType speciesType) {
        this.speciesType = speciesType;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

}

