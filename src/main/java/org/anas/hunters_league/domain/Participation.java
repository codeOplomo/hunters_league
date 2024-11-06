package org.anas.hunters_league.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Participation{

    @Id @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Competition competition;

    @OneToMany(mappedBy = "participation")
    private List<Hunt> hunts;

    private Double score;

    public Participation() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Hunt> getHunts() {
        return hunts;
    }

    public void setHunts(List<Hunt> hunts) {
        this.hunts = hunts;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
