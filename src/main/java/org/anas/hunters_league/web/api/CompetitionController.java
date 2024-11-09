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
        try {
            Competition competition = competitionMapper.toCompetition(saveCompetitionVM);
            System.out.println("Mapped Competition: " + competition);

            Competition addedCompetition = competitionService.addCompetition(competition);

            CompetitionVM addedCompetitionVM = competitionMapper.toCompetitionVM(addedCompetition);
            return new ResponseEntity<>(addedCompetitionVM, HttpStatus.CREATED);

        } catch (InvalidParticipantsException e) {
            // You could choose to return null for CompetitionVM and send an error message as a part of the body
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }




    @GetMapping("/details/{id}")
    public ResponseEntity<CompetitionDetailsVM> getCompetitionById(@PathVariable UUID id) {
        Competition competition = competitionService.getCompetitionById(id);

        if (competition == null) {
            return ResponseEntity.notFound().build();
        }

        // Use the mapper to convert Competition to CompetitionDetailsVM
        CompetitionDetailsVM competitionDetailsVM = competitionMapper.toCompetitionDetailsVM(competition);
        return ResponseEntity.ok(competitionDetailsVM);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerToCompetition(@Valid @RequestBody CompetitionRegisterVM competitionRegisterVM) {
        try {
            competitionService.registerToCompetition(
                    competitionRegisterVM.getUserId(),
                    competitionRegisterVM.getCompetitionId()
            );
            return new ResponseEntity<>("Registration successful for competition: " + competitionRegisterVM.getCompetitionId(), HttpStatus.OK);

        } catch (LicenseExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        } catch (RegistrationClosedException | MaxParticipantsReachedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (CompetitionNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/podium")
    public ResponseEntity<List<PodiumResultDTO>> getCompetitionPodium(@RequestBody @NotNull UUID competitionId) {
        List<PodiumResultDTO> podium = participationService.getCompetitionPodium(competitionId);
        return ResponseEntity.ok(podium);
    }

    @PostMapping("/user/results")
    public ResponseEntity<List<ParticipationHistoryDTO>> getUserCompetitionHistory(@RequestBody @NotNull UUID userId) {
        try {
            List<ParticipationHistoryDTO> results = participationService.getUserCompetitionsHistory(userId);
            return ResponseEntity.ok(results);
        } catch (RuntimeException e) {
            // Log the exception for debugging
            System.err.println("Error fetching competition history: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/compare-results")
    public ResponseEntity<Map<String, List<ParticipationHistoryDTO>>> compareUserCompetitionHistory(
            @Valid @RequestBody MembersComparisonVM membersComparisonVM) {

        try {
            Map<String, List<ParticipationHistoryDTO>> comparisonResult = participationService.compareMemberPerformance(
                    membersComparisonVM.getUserToCompareId(),
                    membersComparisonVM.getUserToCompareWithId()
            );
            return ResponseEntity.ok(comparisonResult);
        } catch (RuntimeException e) {
            // Log the exception for debugging
            System.err.println("Error comparing competition history: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
