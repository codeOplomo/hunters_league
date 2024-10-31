package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.Species;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpeciesRepository extends PagingAndSortingRepository<Species, UUID>, CrudRepository<Species, UUID> {
}
