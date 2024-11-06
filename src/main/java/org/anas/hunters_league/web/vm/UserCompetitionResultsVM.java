package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserCompetitionResultsVM {

    @NotNull(message = "User ID is required.")
    private UUID userId;

    @NotNull(message = "Competition ID is required.")
    private UUID competitionId;

    // Constructors, getters, and setters
    public UserCompetitionResultsVM() {}

    public UserCompetitionResultsVM(UUID userId, UUID competitionId) {
        this.userId = userId;
        this.competitionId = competitionId;
    }

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
