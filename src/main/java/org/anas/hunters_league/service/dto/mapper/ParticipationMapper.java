package org.anas.hunters_league.service.dto.mapper;


import org.anas.hunters_league.domain.Hunt;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.service.dto.ParticipationResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {

    @Mapping(source = "id", target = "participationId")
    @Mapping(source = "competition.location", target = "location")
    @Mapping(source = "competition.code", target = "competitionCode")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "hunts", target = "totalHunts", qualifiedByName = "countHunts")
    ParticipationResultDTO toParticipationResultDTO(Participation participation);

    @Named("countHunts")
    default int countHunts(List<Hunt> hunts) {
        return hunts != null ? hunts.size() : 0;
    }
}
