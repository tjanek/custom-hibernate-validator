package pl.tjanek.web.test.impl;

import javax.validation.ConstraintValidatorContext;

@FunctionalInterface
public interface ValidationStrategy<T> {

    boolean validate(T validated, ConstraintValidatorContext context);

}
