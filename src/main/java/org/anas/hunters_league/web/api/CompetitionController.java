package org.anas.hunters_league.web.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.anas.hunters_league.annotations.RequiresPermission;
import org.anas.hunters_league.annotations.RequiresRole;
import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.domain.enums.Permission;
import org.anas.hunters_league.domain.enums.Role;
import org.anas.hunters_league.exceptions.*;
import org.anas.hunters_league.service.CompetitionService;
import org.anas.hunters_league.service.ParticipationService;
import org.anas.hunters_league.service.dto.ParticipationHistoryDTO;
import org.anas.hunters_league.service.dto.PodiumResultDTO;
import org.anas.hunters_league.web.vm.*;
import org.anas.hunters_league.web.vm.mapper.CompetitionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<CompetitionVM> addCompetition(@Valid @RequestBody SaveCompetitionVM saveCompetitionVM) {
        System.out.println("Attempting to add competition");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.err.println("No authentication found");
            throw new AccessDeniedException("No authentication present");
        }

        // More detailed authentication logging
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Credentials: " + authentication.getCredentials());
        System.out.println("Is Authenticated: " + authentication.isAuthenticated());

        // Print out all authorities with more context
        System.out.println("Current User Authorities:");
        authentication.getAuthorities().forEach(authority ->
                System.out.println("Authority: " + authority.getAuthority())
        );

        // Verify the role explicitly
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            System.err.println("User does not have ADMIN role");
            throw new AccessDeniedException("Insufficient permissions");
        }

        Competition competition = competitionMapper.toCompetition(saveCompetitionVM);
        Competition addedCompetition = competitionService.addCompetition(competition);
        CompetitionVM addedCompetitionVM = competitionMapper.toCompetitionVM(addedCompetition);
        return new ResponseEntity<>(addedCompetitionVM, HttpStatus.CREATED);
    }

    @RequiresRole(Role.MEMBER)
    @GetMapping("/details/{id}")
    public ResponseEntity<CompetitionDetailsVM> getCompetitionById(@PathVariable UUID id) {
        Competition competition = competitionService.getCompetitionById(id);

        CompetitionDetailsVM competitionDetailsVM = competitionMapper.toCompetitionDetailsVM(competition);
        return ResponseEntity.ok(competitionDetailsVM);
    }


    @RequiresRole(Role.MEMBER)
    @PostMapping("/register")
    public ResponseEntity<String> registerToCompetition(@Valid @RequestBody CompetitionRegisterVM competitionRegisterVM) {
        competitionService.registerToCompetition(
                competitionRegisterVM.getUserId(),
                competitionRegisterVM.getCompetitionId()
        );
        return new ResponseEntity<>("Registration successful for competition: " + competitionRegisterVM.getCompetitionId(), HttpStatus.OK);
    }


    @RequiresRole(Role.MEMBER)
    @PostMapping("/podium")
    public ResponseEntity<List<PodiumResultDTO>> getCompetitionPodium(@RequestBody @Valid CompetitionIdVM request) {
        List<PodiumResultDTO> podium = participationService.getCompetitionPodium(request.getCompetitionId());
        return ResponseEntity.ok(podium);
    }


    @RequiresRole(Role.MEMBER)
    @PostMapping("/user/results")
    public ResponseEntity<List<ParticipationHistoryDTO>> getUserCompetitionHistory(@RequestBody @Valid UserIdVM request) {
        List<ParticipationHistoryDTO> results = participationService.getUserCompetitionsHistory(request.getUserId());
        return ResponseEntity.ok(results);
    }


    @RequiresRole(Role.MEMBER)
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
