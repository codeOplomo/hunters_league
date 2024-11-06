package org.anas.hunters_league.service.dto;

import java.util.UUID;

public class ParticipationResultDTO {
    private UUID participationId;
    private String location;
    private String competitionCode;
    private Double score;
    private int totalHunts;

    // Constructor
    public ParticipationResultDTO() {
    }

    // Getters and Setters
    public UUID getParticipationId() {
        return participationId;
    }

    public String getLocation() {
        return location;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public Double getScore() {
        return score;
    }

    public int getTotalHunts() {
        return totalHunts;
    }

    public void setParticipationId(UUID participationId) {
        this.participationId = participationId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }


    public void setScore(Double score) {
        this.score = score;
    }

    public void setTotalHunts(int totalHunts) {
        this.totalHunts = totalHunts;
    }
}

