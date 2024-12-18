package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserIdVM {

    @NotNull
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
