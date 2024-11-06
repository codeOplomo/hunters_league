package org.anas.hunters_league.web.vm.mapper;

import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.web.vm.CompetitionDetailsVM;
import org.anas.hunters_league.web.vm.CompetitionVM;
import org.anas.hunters_league.web.vm.SaveCompetitionVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    Competition toCompetition(SaveCompetitionVM vm);

    CompetitionVM toCompetitionVM(Competition competition);

    CompetitionDetailsVM toCompetitionDetailsVM(Competition competition);
}

