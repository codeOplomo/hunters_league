package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CompetitionIdVM {

    @NotNull
    private UUID competitionId;

    public UUID getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(UUID competitionId) {
        this.competitionId = competitionId;
    }
}
