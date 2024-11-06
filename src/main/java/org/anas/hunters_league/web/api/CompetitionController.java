package org.anas.hunters_league.web.api;

import jakarta.validation.Valid;
import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.service.CompetitionService;
import org.anas.hunters_league.web.vm.CompetitionDetailsVM;
import org.anas.hunters_league.web.vm.CompetitionRegisterVM;
import org.anas.hunters_league.web.vm.CompetitionVM;
import org.anas.hunters_league.web.vm.SaveCompetitionVM;
import org.anas.hunters_league.web.vm.mapper.CompetitionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;

    public CompetitionController(CompetitionService competitionService, CompetitionMapper competitionMapper) {
        this.competitionService = competitionService;
        this.competitionMapper = competitionMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<CompetitionVM> addCompetition(@Valid @RequestBody SaveCompetitionVM saveCompetitionVM) {
        Competition competition = competitionMapper.toCompetition(saveCompetitionVM);
        System.out.println("Mapped Competition: " + competition); // Log mapped entity

        Competition addedCompetition = competitionService.addCompetition(competition);

        CompetitionVM addedCompetitionVM = competitionMapper.toCompetitionVM(addedCompetition);
        return new ResponseEntity<>(addedCompetitionVM, HttpStatus.CREATED);
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
            // Call the service method to register the user to the competition
            Participation participation = competitionService.registerToCompetition(
                    competitionRegisterVM.getUserId(),
                    competitionRegisterVM.getCompetitionId()
            );
            return new ResponseEntity<>("Registration successful for competition: " + competitionRegisterVM.getCompetitionId(), HttpStatus.OK);
        } catch (IllegalStateException e) {
            // If registration fails due to closed registration or max participants
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Catch any other runtime exceptions, such as user not found or competition not found
            return new ResponseEntity<>("Error processing registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
