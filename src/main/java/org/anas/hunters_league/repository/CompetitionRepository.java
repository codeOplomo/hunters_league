package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    // Additional query methods can be added here if needed
    Optional<Competition> findByCode(String competitionCode);
    boolean existsByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

