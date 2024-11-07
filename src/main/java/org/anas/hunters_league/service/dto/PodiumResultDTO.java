package org.anas.hunters_league.service.dto;

import java.util.UUID;

public class PodiumResultDTO {
    private UUID userId;
    private String username;
    private Double score;

    // Constructor, Getters, and Setters
    public PodiumResultDTO() {
    }

    public PodiumResultDTO(UUID userId, String username, Double score) {
        this.userId = userId;
        this.username = username;
        this.score = score;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}

