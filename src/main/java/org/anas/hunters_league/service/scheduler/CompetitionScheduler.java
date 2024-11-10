package org.anas.hunters_league.service.scheduler;

import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.repository.CompetitionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CompetitionScheduler {

    private final CompetitionRepository competitionRepository;

    public CompetitionScheduler(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void closeRegistrationsAutomatically() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusHours(24);

        List<Competition> competitions = competitionRepository.findByOpenRegistrationTrue();

        for (Competition competition : competitions) {
            if (competition.getDate().isBefore(deadline)) {
                competition.setOpenRegistration(false);
                competitionRepository.save(competition);
            }
        }
    }
}