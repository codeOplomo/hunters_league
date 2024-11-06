package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CompetitionRegisterVM {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Competition ID is required")
    private UUID competitionId;

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(UUID competitionId) {
        this.competitionId = competitionId;
    }
}
