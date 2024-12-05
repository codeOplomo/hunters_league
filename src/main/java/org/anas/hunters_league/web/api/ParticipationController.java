package org.anas.hunters_league.web.api;
import jakarta.validation.Valid;
import org.anas.hunters_league.annotations.RequiresPermission;
import org.anas.hunters_league.domain.enums.Permission;
import org.anas.hunters_league.exceptions.HuntWeightBelowMinimumException;
import org.anas.hunters_league.exceptions.ParticipationNotFoundException;
import org.anas.hunters_league.exceptions.SpeciesNotFoundException;
import org.anas.hunters_league.service.ParticipationService;
import org.anas.hunters_league.service.dto.ParticipationHistoryDTO;
import org.anas.hunters_league.web.vm.CompetitionResultsRecordVM;
import org.anas.hunters_league.web.vm.UserCompetitionResultsVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/participations")
public class ParticipationController {
    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @RequiresPermission(Permission.CAN_SCORE)
    @PostMapping("/recordResults")
    public ResponseEntity<Map<String, Object>> recordResults(@Valid @RequestBody CompetitionResultsRecordVM competitionResultsRecordVM) {
        Double updatedScore = participationService.recordResults(
                competitionResultsRecordVM.getParticipationId(),
                competitionResultsRecordVM.getHunts()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Results recorded successfully");
        response.put("participationId", competitionResultsRecordVM.getParticipationId());
        response.put("totalScore", updatedScore);
        response.put("numberOfHunts", competitionResultsRecordVM.getHunts().size());

        return ResponseEntity.ok(response);
    }



    @PostMapping("/competition-results")
    public ResponseEntity<List<ParticipationHistoryDTO.ParticipationDetailsDTO>> getUserCompetitionResults(
            @Valid @RequestBody UserCompetitionResultsVM userCompetitionVM) {
        List<ParticipationHistoryDTO.ParticipationDetailsDTO> results = participationService.getUserCompetitionResults(
                userCompetitionVM.getUserId(), userCompetitionVM.getCompetitionId());
        return ResponseEntity.ok(results);
    }

}
