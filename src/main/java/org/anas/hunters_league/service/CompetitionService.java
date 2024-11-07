package org.anas.hunters_league.service;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.repository.CompetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final AppUserService appUserService;
    private final ParticipationService participationService;

    public CompetitionService(CompetitionRepository competitionRepository, AppUserService appUserService, ParticipationService participationService) {
        this.competitionRepository = competitionRepository;
        this.appUserService = appUserService;
        this.participationService = participationService;
    }

    public Participation registerToCompetition(UUID userId, UUID competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        if (!competition.getOpenRegistration()) {
            throw new IllegalStateException("Registration is closed for this competition");
        }

        if (competition.getParticipations().size() >= competition.getMaxParticipants()) {
            throw new IllegalStateException("This competition has reached the maximum number of participants");
        }

        AppUser user = appUserService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setCompetition(competition);
        participation.setScore(0.0);

        return participationService.save(participation);
    }

    @Transactional
    public Competition addCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    public Competition getCompetitionById(UUID id) {
        return competitionRepository.findById(id).orElse(null);
    }

    public Competition getCompetitionByCode(String competitionCode) {
        return competitionRepository.findByCode(competitionCode)
                .orElseThrow(() -> new RuntimeException("Competition with code " + competitionCode + " not found"));
    }
}

