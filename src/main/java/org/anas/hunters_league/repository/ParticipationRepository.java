package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
    // Additional query methods can be added here if needed
    List<Participation> findByUserIdAndCompetitionId(UUID userId, UUID competitionId);
}
