package org.anas.hunters_league.web.vm.mapper;

import org.anas.hunters_league.domain.Species;
import org.anas.hunters_league.web.vm.SaveSpeciesVM;
import org.anas.hunters_league.web.vm.SpeciesVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpeciesMapper {

    // Mapping from Species entity to SpeciesVM

    SpeciesVM toSpeciesVM(Species species);


    Species toSpecies(SaveSpeciesVM saveSpeciesVM);


    // New method to update species from SaveSpeciesVM
    void updateSpeciesFromVM(SaveSpeciesVM saveSpeciesVM, @MappingTarget Species species);
}

