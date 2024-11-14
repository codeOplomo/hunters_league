package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.Species;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpeciesRepository extends PagingAndSortingRepository<Species, UUID>, CrudRepository<Species, UUID> {

    @Modifying
    @Query(value = "CALL DeleteSpeciesAndRelationsV2(:speciesId)", nativeQuery = true)
    void deleteSpeciesAndRelations(@Param("speciesId") UUID speciesId);

}
