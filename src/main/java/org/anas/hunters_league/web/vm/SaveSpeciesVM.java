package org.anas.hunters_league.web.vm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.anas.hunters_league.domain.enums.Difficulty;
import org.anas.hunters_league.domain.enums.SpeciesType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveSpeciesVM {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Category is mandatory")
    private SpeciesType category;

    @Min(value = 0, message = "Minimum weight must be greater than or equal to 0")
    private Double minimumWeight;

    @NotNull(message = "Difficulty is mandatory")
    private Difficulty difficulty;

    @Min(value = 0, message = "Points must be greater than or equal to 0")
    private Integer points;
}
