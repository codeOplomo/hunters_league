package org.anas.hunters_league.service.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParticipationHistoryDTO {
    private String location;
    private String competitionCode;
    private LocalDateTime date;
    private int rank;
    private Double totalScore;
    private List<ParticipationDetailsDTO> participations; // List of individual participation details

    public ParticipationHistoryDTO() {
        this.participations = new ArrayList<>();
    }

    // Inner class to represent each participation entry
    public static class ParticipationDetailsDTO {
        private UUID participationId;
        private Double score;
        private int totalHunts;

        // Getters and setters
        public UUID getParticipationId() { return participationId; }
        public void setParticipationId(UUID participationId) { this.participationId = participationId; }

        public Double getScore() { return score; }
        public void setScore(Double score) { this.score = score; }

        public int getTotalHunts() { return totalHunts; }
        public void setTotalHunts(int totalHunts) { this.totalHunts = totalHunts; }
    }

    // Getters and setters for outer DTO
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCompetitionCode() { return competitionCode; }
    public void setCompetitionCode(String competitionCode) { this.competitionCode = competitionCode; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

    public List<ParticipationDetailsDTO> getParticipations() { return participations; }
    public void setParticipations(List<ParticipationDetailsDTO> participations) { this.participations = participations; }
}

