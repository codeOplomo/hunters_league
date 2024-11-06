package org.anas.hunters_league.service;

import org.anas.hunters_league.domain.Hunt;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.domain.Species;
import org.anas.hunters_league.repository.ParticipationRepository;
import org.anas.hunters_league.service.dto.ParticipationResultDTO;
import org.anas.hunters_league.service.dto.mapper.ParticipationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final SpeciesService speciesService;
    private final ParticipationMapper participationMapper;

    public ParticipationService(ParticipationRepository participationRepository, SpeciesService speciesService, ParticipationMapper participationMapper) {
        this.participationRepository = participationRepository;
        this.speciesService = speciesService;
        this.participationMapper = participationMapper;
    }

    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    public Double recordResults(UUID participationId, List<Hunt> hunts) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        double totalScore = hunts.stream()
                .mapToDouble(hunt -> {
                    Species species = speciesService.findById(hunt.getSpecies().getId())
                            .orElseThrow(() -> new RuntimeException("Species not found"));

                    // Retrieve species and difficulty factors
                    double speciesFactor = species.getCategory().getValue();
                    double difficultyFactor = species.getDifficulty().getValue();

                    // Calculate the score for this hunt
                    return species.getPoints() + (hunt.getWeight() * speciesFactor * difficultyFactor);
                })
                .sum();

        // Update the participation score
        participation.setScore(totalScore);
        participationRepository.save(participation);

        return totalScore;
    }

    public List<ParticipationResultDTO> getUserCompetitionResults(UUID userId, UUID competitionId) {
        List<Participation> participations = participationRepository.findByUserIdAndCompetitionId(userId, competitionId);
        return participations.stream()
                .map(participationMapper::toParticipationResultDTO)
                .collect(Collectors.toList());
    }

}

