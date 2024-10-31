package org.anas.hunters_league.web.vm;

import lombok.Getter;
import lombok.Setter;
import org.anas.hunters_league.domain.enums.Difficulty;
import org.anas.hunters_league.domain.enums.SpeciesType;

import java.util.UUID;


@Getter
@Setter
public class SpeciesVM {
    private UUID id;
    private String name;
    private SpeciesType category;
    private Double minimumWeight;
    private Difficulty difficulty;
    private Integer points;

}

