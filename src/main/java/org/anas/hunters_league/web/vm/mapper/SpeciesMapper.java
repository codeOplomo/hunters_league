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
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "minimumWeight", source = "minimumWeight")
    @Mapping(target = "difficulty", source = "difficulty")
    @Mapping(target = "points", source = "points")
    SpeciesVM toSpeciesVM(Species species);


    @Mapping(target = "id", ignore = true)
    Species toSpecies(SaveSpeciesVM saveSpeciesVM);


    // New method to update species from SaveSpeciesVM
    @Mapping(target = "id", ignore = true)
    void updateSpeciesFromVM(SaveSpeciesVM saveSpeciesVM, @MappingTarget Species species);
}

