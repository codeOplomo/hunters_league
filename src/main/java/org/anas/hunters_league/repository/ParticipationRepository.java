package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.service.dto.PodiumResultDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {

    @Query("SELECT new org.anas.hunters_league.service.dto.PodiumResultDTO(p.user.id, p.user.username, p.score) " +
            "FROM Participation p WHERE p.competition.id = :competitionId ORDER BY p.score DESC")
    List<PodiumResultDTO> findTop3ByCompetitionIdOrderByScoreDesc(UUID competitionId, Pageable pageable);

    // Additional query methods can be added here if needed
    List<Participation> findByUserIdAndCompetitionId(UUID userId, UUID competitionId);

    List<Participation> findByUserId(UUID userId);

    List<Participation> findByCompetitionId(UUID competitionId);

    @Query("SELECT p.competition.id AS competitionId, " +
            "p.competition.location AS location, " +
            "p.competition.code AS competitionCode, " +
            "p.competition.date AS date, " +
            "p.user.id AS userId, " +
            "SUM(p.score) AS totalScore " +
            "FROM Participation p " +
            "WHERE p.user.id = :userId " +
            "GROUP BY p.competition.id, p.user.id " +
            "ORDER BY totalScore DESC")
    List<Object[]> findUserCompetitionHistory(UUID userId);

}
