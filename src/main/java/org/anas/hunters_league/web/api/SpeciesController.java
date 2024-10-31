package org.anas.hunters_league.web.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.anas.hunters_league.domain.Species;
import org.anas.hunters_league.service.SpeciesService;
import org.anas.hunters_league.web.vm.SaveSpeciesVM;
import org.anas.hunters_league.web.vm.SpeciesVM;
import org.anas.hunters_league.web.vm.mapper.SpeciesMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {

    private final SpeciesService speciesService;
    private final SpeciesMapper speciesMapper;

    public SpeciesController(SpeciesService speciesService, SpeciesMapper speciesMapper) {
        this.speciesService = speciesService;
        this.speciesMapper = speciesMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SpeciesVM>> getAllSpecies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Species> speciesPage = speciesService.getAllSpecies(pageable);
        Page<SpeciesVM> speciesVMs = speciesPage.map(speciesMapper::toSpeciesVM);

        return ResponseEntity.ok(speciesVMs);
    }


    @PostMapping("/add")
    public ResponseEntity<SpeciesVM> addSpecies(@Valid @RequestBody SaveSpeciesVM saveSpeciesVM) {
        System.out.println("HELLLOOOO");
        Species species = speciesMapper.toSpecies(saveSpeciesVM); // Convert to Species here
        Species addedSpecies = speciesService.addSpecies(species);
        SpeciesVM addedSpeciesVM = speciesMapper.toSpeciesVM(addedSpecies);
        return new ResponseEntity<>(addedSpeciesVM, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<SpeciesVM> updateSpecies(
            @PathVariable UUID id,
            @Valid @RequestBody SaveSpeciesVM saveSpeciesVM) {

        Species existingSpecies = speciesService.getSpeciesById(id);

        if (existingSpecies == null) {
            return ResponseEntity.notFound().build(); // Return 404 if species not found
        }

        // Map updated values from SaveSpeciesVM to existing Species
        speciesMapper.updateSpeciesFromVM(saveSpeciesVM, existingSpecies);

        Species updatedSpecies = speciesService.addSpecies(existingSpecies); // Re-save to update
        SpeciesVM updatedSpeciesVM = speciesMapper.toSpeciesVM(updatedSpecies);

        return ResponseEntity.ok(updatedSpeciesVM);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecies(@PathVariable UUID id) {
        try {
            speciesService.deleteSpecies(id);
            return ResponseEntity.ok("Species with ID " + id + " was successfully deleted.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Species with ID " + id + " not found.");
        }
    }

}
