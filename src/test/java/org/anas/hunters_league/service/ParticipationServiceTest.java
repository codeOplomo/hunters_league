package org.anas.hunters_league.service;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.domain.Competition;
import org.anas.hunters_league.domain.Hunt;
import org.anas.hunters_league.domain.Participation;
import org.anas.hunters_league.exceptions.DuplicateParticipationException;
import org.anas.hunters_league.exceptions.UserNotFoundException;
import org.anas.hunters_league.repository.ParticipationRepository;
import org.anas.hunters_league.service.dto.mapper.ParticipationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private SpeciesService speciesService;

    @Mock
    private AppUserService userService;

    @Mock
    private ParticipationMapper participationMapper;

    @InjectMocks
    private ParticipationService participationService;

    private Participation participation;
    private AppUser user;
    private Competition competition;

    @BeforeEach
    public void setUp() {
        user = new AppUser();
        competition = new Competition();
        participation = new Participation();
        participation.setUser(user);
        participation.setCompetition(competition);
        participation.setScore(100.0);
    }

//    @Test
//    public void testSaveParticipation() {
//        // Given
//        when(participationRepository.save(any(Participation.class))).thenReturn(participation);
//
//        // When
//        Participation savedParticipation = participationService.save(participation);
//
//        // Then
//        assertNotNull(savedParticipation);
//        verify(participationRepository, times(1)).save(participation);
//    }

//    @Test
//    public void testSaveDuplicateParticipation() {
//        // Given
//        UUID userId = user.getId();
//        UUID competitionId = competition.getId();
//        // Simulate an existing participation in the competition
//        when(participationRepository.findByUserIdAndCompetitionId(userId, competitionId))
//                .thenReturn(Collections.singletonList(participation));
//
//        // When and Then
//        assertThrows(DuplicateParticipationException.class, () -> participationService.save(participation));
//    }

    @Test
    public void testSaveUserNotFound() {
        // Given
        UUID userId = user.getId();
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> participationService.save(participation));
    }


    @Test
    public void testRecordResults_EmptyHuntList() {
        // Given
        UUID participationId = participation.getId();
        List<Hunt> hunts = Collections.emptyList();

        // Mock the repository to return the participation
        when(participationRepository.findById(participationId)).thenReturn(Optional.of(participation));

        // When
        double totalScore = participationService.recordResults(participationId, hunts);

        // Then
        assertEquals(0.0, totalScore, 0.01);
        verify(participationRepository, times(1)).save(participation); // Verify that participation was saved
    }

}
