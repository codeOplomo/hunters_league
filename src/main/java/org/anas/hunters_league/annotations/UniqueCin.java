package org.anas.hunters_league.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.anas.hunters_league.validators.UniqueCinValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCinValidator.class)
public @interface UniqueCin {
    String message() default "CIN already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
