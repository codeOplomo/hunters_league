package org.anas.hunters_league.web.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.exceptions.*;
import org.anas.hunters_league.service.CompetitionService;
import org.anas.hunters_league.service.ParticipationService;
import org.anas.hunters_league.service.dto.ParticipationHistoryDTO;
import org.anas.hunters_league.service.dto.PodiumResultDTO;
import org.anas.hunters_league.web.vm.*;
import org.anas.hunters_league.web.vm.mapper.CompetitionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;
    private final ParticipationService participationService;

    public CompetitionController(CompetitionService competitionService, CompetitionMapper competitionMapper, ParticipationService participationService) {
        this.competitionService = competitionService;
        this.competitionMapper = competitionMapper;
        this.participationService = participationService;
    }

    @PostMapping("/add")
    public ResponseEntity<CompetitionVM> addCompetition(@Valid @RequestBody SaveCompetitionVM saveCompetitionVM) {
        Competition competition = competitionMapper.toCompetition(saveCompetitionVM);
        Competition addedCompetition = competitionService.addCompetition(competition);
        CompetitionVM addedCompetitionVM = competitionMapper.toCompetitionVM(addedCompetition);
        return new ResponseEntity<>(addedCompetitionVM, HttpStatus.CREATED);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<CompetitionDetailsVM> getCompetitionById(@PathVariable UUID id) {
        Competition competition = competitionService.getCompetitionById(id);

        CompetitionDetailsVM competitionDetailsVM = competitionMapper.toCompetitionDetailsVM(competition);
        return ResponseEntity.ok(competitionDetailsVM);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerToCompetition(@Valid @RequestBody CompetitionRegisterVM competitionRegisterVM) {
        competitionService.registerToCompetition(
                competitionRegisterVM.getUserId(),
                competitionRegisterVM.getCompetitionId()
        );
        return new ResponseEntity<>("Registration successful for competition: " + competitionRegisterVM.getCompetitionId(), HttpStatus.OK);
    }

    @PostMapping("/podium")
    public ResponseEntity<List<PodiumResultDTO>> getCompetitionPodium(@RequestBody @NotNull UUID competitionId) {
        List<PodiumResultDTO> podium = participationService.getCompetitionPodium(competitionId);
        return ResponseEntity.ok(podium);
    }

    @PostMapping("/user/results")
    public ResponseEntity<List<ParticipationHistoryDTO>> getUserCompetitionHistory(@RequestBody @NotNull UUID userId) {
        List<ParticipationHistoryDTO> results = participationService.getUserCompetitionsHistory(userId);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/user/compare-results")
    public ResponseEntity<Map<String, List<ParticipationHistoryDTO>>> compareUserCompetitionHistory(
            @Valid @RequestBody MembersComparisonVM membersComparisonVM) {

        Map<String, List<ParticipationHistoryDTO>> comparisonResult = participationService.compareMemberPerformance(
                membersComparisonVM.getUserToCompareId(),
                membersComparisonVM.getUserToCompareWithId()
        );
        return ResponseEntity.ok(comparisonResult);
    }

}
