package org.anas.hunters_league.web.vm;

import org.anas.hunters_league.domain.Competition;

import java.time.LocalDateTime;


public class CompetitionDetailsVM {

    private LocalDateTime date;
    private String location;
    private Integer participantCount;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }


    private Integer getParticipantCount(Competition competition) {
        return competition.getParticipations().size();
    }
}
