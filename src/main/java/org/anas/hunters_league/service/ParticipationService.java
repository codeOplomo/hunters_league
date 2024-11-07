package org.anas.hunters_league.service;

import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Hunt;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.domain.Species;
import org.anas.hunters_league.repository.ParticipationRepository;
import org.anas.hunters_league.service.dto.ParticipationHistoryDTO;
import org.anas.hunters_league.service.dto.PodiumResultDTO;
import org.anas.hunters_league.service.dto.mapper.ParticipationMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final SpeciesService speciesService;
    private final ParticipationMapper participationMapper;
    private final CompetitionService competitionService;

    public ParticipationService(ParticipationRepository participationRepository,
                                SpeciesService speciesService,
                                ParticipationMapper participationMapper,
                                @Lazy CompetitionService competitionService) {
        this.participationRepository = participationRepository;
        this.speciesService = speciesService;
        this.participationMapper = participationMapper;
        this.competitionService = competitionService;
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

    public List<ParticipationHistoryDTO.ParticipationDetailsDTO> getUserCompetitionResults(UUID userId, UUID competitionId) {
        List<Participation> participations = participationRepository.findByUserIdAndCompetitionId(userId, competitionId);
        return participations.stream()
                .map(participationMapper::toParticipationDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<ParticipationHistoryDTO> getUserCompetitionsHistory(UUID userId) {
        // Fetch and group participations by competition
        List<Participation> participations = participationRepository.findByUserId(userId);
        Map<UUID, List<Participation>> userParticipationsByCompetition = participations.stream()
                .collect(Collectors.groupingBy(p -> p.getCompetition().getId()));

        List<ParticipationHistoryDTO> participationHistory = new ArrayList<>();

        for (UUID competitionId : userParticipationsByCompetition.keySet()) {
            List<Participation> competitionParticipations = userParticipationsByCompetition.get(competitionId);

            // Calculate total scores for ranking
            Map<UUID, Double> totalScoresByUser = competitionParticipations.stream()
                    .collect(Collectors.groupingBy(
                            p -> p.getUser().getId(),
                            Collectors.summingDouble(Participation::getScore)
                    ));

            // Rank users by total score
            List<Map.Entry<UUID, Double>> sortedScores = totalScoresByUser.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .collect(Collectors.toList());

            int rank = 1;
            Double userTotalScore = totalScoresByUser.get(userId);
            for (Map.Entry<UUID, Double> entry : sortedScores) {
                if (entry.getKey().equals(userId)) break;
                rank++;
            }

            // Map competition data using ParticipationMapper
            ParticipationHistoryDTO competitionHistoryDTO = participationMapper.toParticipationHistoryDTO(competitionParticipations.get(0));
            competitionHistoryDTO.setRank(rank);
            competitionHistoryDTO.setTotalScore(userTotalScore);

            // Map each participation to ParticipationDetailsDTO and add to the list
            List<ParticipationHistoryDTO.ParticipationDetailsDTO> detailsList = competitionParticipations.stream()
                    .map(participationMapper::toParticipationDetailsDTO)
                    .collect(Collectors.toList());

            competitionHistoryDTO.setParticipations(detailsList);
            participationHistory.add(competitionHistoryDTO);
        }

        return participationHistory;
    }

    public Map<String, List<ParticipationHistoryDTO>> compareMemberPerformance(UUID userToCompareId, UUID userToCompareWithId) {
        List<ParticipationHistoryDTO> userToCompareHistory = getUserCompetitionsHistory(userToCompareId);
        List<ParticipationHistoryDTO> userToCompareWithHistory = getUserCompetitionsHistory(userToCompareWithId);

        // Return the results in a map for structured response
        Map<String, List<ParticipationHistoryDTO>> comparisonResult = new HashMap<>();
        comparisonResult.put("User To Compare History", userToCompareHistory);
        comparisonResult.put("User To Compare With History", userToCompareWithHistory);

        return comparisonResult;
    }



    public List<PodiumResultDTO> getCompetitionPodium(UUID competitionId) {
        Pageable topThree = PageRequest.of(0, 3);
        return participationRepository.findTop3ByCompetitionIdOrderByScoreDesc(competitionId, topThree);
    }
}

