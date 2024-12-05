package org.anas.hunters_league.service;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.exceptions.*;
import org.anas.hunters_league.repository.CompetitionRepository;
import org.anas.hunters_league.utils.CompetitionCodeGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
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


    @Transactional
    public Competition addCompetition(Competition competition) {
        if (competition.getMinParticipants() == null || competition.getMaxParticipants() == null) {
            throw new InvalidParticipantsException("Both minimum and maximum participants must be defined.");
        }

        if (competition.getMinParticipants() >= competition.getMaxParticipants()) {
            throw new InvalidParticipantsException("Minimum participants must be less than maximum participants.");
        }

        if (isCompetitionScheduledForThisWeek(competition.getDate())) {
            throw new InvalidParticipantsException("There can only be one competition scheduled per week.");
        }

        String generatedCode = CompetitionCodeGenerator.generateCode(competition.getLocation(), competition.getDate());
        competition.setCode(generatedCode);
        competition.setOpenRegistration(true);

        return competitionRepository.save(competition);
    }

    public Participation registerToCompetition(UUID userId, UUID competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new CompetitionNotFoundException("Competition not found"));

        if (!competition.getOpenRegistration()) {
            throw new RegistrationClosedException("Registration is closed for this competition");
        }

        if (competition.getParticipations().size() >= competition.getMaxParticipants()) {
            throw new MaxParticipantsReachedException("This competition has reached the maximum number of participants");
        }

        AppUser user = appUserService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getLicenseExpirationDate() != null && user.getLicenseExpirationDate().isBefore(LocalDateTime.now())) {
            throw new LicenseExpiredException("User's license has expired. Unable to register for the competition.");
        }

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setCompetition(competition);
        participation.setScore(0.0);

        return participationService.save(participation);
    }


    private boolean isCompetitionScheduledForThisWeek(LocalDateTime competitionDate) {
        LocalDateTime startOfWeek = competitionDate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).toLocalDate().atStartOfDay();

        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);

        return competitionRepository.existsByDateBetween(startOfWeek, endOfWeek);
    }


    public Competition getCompetitionById(UUID id) {
        return competitionRepository.findById(id).orElseThrow(() -> new CompetitionNotFoundException("Competition with ID " + id + " not found"));
    }

    public Competition getCompetitionByCode(String competitionCode) {
        return competitionRepository.findByCode(competitionCode)
                .orElseThrow(() -> new RuntimeException("Competition with code " + competitionCode + " not found"));
    }
}

