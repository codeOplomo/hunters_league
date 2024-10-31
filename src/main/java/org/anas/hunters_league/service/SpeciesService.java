package org.anas.hunters_league.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.anas.hunters_league.domain.Species;
import org.anas.hunters_league.repository.SpeciesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Page<Species> getAllSpecies(Pageable pageable) {
        return speciesRepository.findAll(pageable);
    }

    @Transactional
    public Species addSpecies(Species species) {
        return speciesRepository.save(species);
    }

    public Species getSpeciesById(UUID id) {
        return speciesRepository.findById(id).orElse(null); // Return null if not found
    }

    @Transactional
    public void deleteSpecies(UUID id) {
        // Check if the species exists, then delete it
        if (!speciesRepository.existsById(id)) {
            throw new EntityNotFoundException("Species not found with ID: " + id);
        }
        speciesRepository.deleteById(id);
    }

}
