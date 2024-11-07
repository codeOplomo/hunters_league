package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class MembersComparisonVM {

    @NotNull(message = "User to compare ID must not be null")
    private UUID userToCompareId;

    @NotNull(message = "User to compare with ID must not be null")
    private UUID userToCompareWithId;

    // Getters and Setters
    public UUID getUserToCompareId() {
        return userToCompareId;
    }

    public void setUserToCompareId(UUID userToCompareId) {
        this.userToCompareId = userToCompareId;
    }

    public UUID getUserToCompareWithId() {
        return userToCompareWithId;
    }

    public void setUserToCompareWithId(UUID userToCompareWithId) {
        this.userToCompareWithId = userToCompareWithId;
    }
}
