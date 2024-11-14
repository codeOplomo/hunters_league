package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface HuntRepository extends JpaRepository<Hunt, UUID> {
    @Modifying
    @Query("DELETE FROM Hunt h WHERE h.species.id = :speciesId")
    void deleteBySpeciesId(@Param("speciesId") UUID speciesId);

}
