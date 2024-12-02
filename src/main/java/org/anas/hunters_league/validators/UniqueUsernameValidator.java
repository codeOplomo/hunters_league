package org.anas.hunters_league.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.anas.hunters_league.annotations.UniqueUsername;
import org.anas.hunters_league.repository.AppUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final AppUserRepository userRepository;

    public UniqueUsernameValidator(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && userRepository.findByUsername(username).isEmpty();
    }
}
