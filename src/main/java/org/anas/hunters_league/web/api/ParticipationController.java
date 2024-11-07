package org.anas.hunters_league.web.api;
import jakarta.validation.Valid;
import org.anas.hunters_league.service.ParticipationService;
import org.anas.hunters_league.service.dto.ParticipationHistoryDTO;
import org.anas.hunters_league.web.vm.CompetitionResultsRecordVM;
import org.anas.hunters_league.web.vm.UserCompetitionResultsVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/participations")
public class ParticipationController {
    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/recordResults")
    public ResponseEntity<Double> recordResults(@Valid @RequestBody CompetitionResultsRecordVM competitionResultsRecordVM) {
        try {
            Double updatedScore = participationService.recordResults(competitionResultsRecordVM.getParticipationId(), competitionResultsRecordVM.getHunts());
            return ResponseEntity.ok(updatedScore);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/competition-results")
    public ResponseEntity<List<ParticipationHistoryDTO.ParticipationDetailsDTO>> getUserCompetitionResults(
            @Valid @RequestBody UserCompetitionResultsVM userCompetitionVM) {
        List<ParticipationHistoryDTO.ParticipationDetailsDTO> results = participationService.getUserCompetitionResults(
                userCompetitionVM.getUserId(), userCompetitionVM.getCompetitionId());
        return ResponseEntity.ok(results);
    }

}
