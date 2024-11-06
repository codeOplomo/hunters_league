package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.anas.hunters_league.domain.Hunt;

import java.util.List;
import java.util.UUID;

public class CompetitionResultsRecordVM {
    @NotNull(message = "Participation ID cannot be null")
    private UUID participationId;

    @NotNull(message = "Hunts cannot be null")
    @Size(min = 1, message = "At least one hunt must be provided")
    private List<Hunt> hunts;

    public CompetitionResultsRecordVM() {}

    public CompetitionResultsRecordVM(UUID participationId, List<Hunt> hunts) {
        this.participationId = participationId;
        this.hunts = hunts;
    }

    // Getters and setters
    public UUID getParticipationId() {
        return participationId;
    }

    public void setParticipationId(UUID participationId) {
        this.participationId = participationId;
    }

    public List<Hunt> getHunts() {
        return hunts;
    }

    public void setHunts(List<Hunt> hunts) {
        this.hunts = hunts;
    }
}
