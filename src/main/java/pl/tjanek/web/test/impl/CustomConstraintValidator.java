package pl.tjanek.web.test.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public interface CustomConstraintValidator<A extends Annotation, V> extends ConstraintValidator<A, V> {

    default List<CustomValidator<V>> steps() {
        return new ArrayList<>();
    }

    default void initialize(A constraintAnnotation) {
    }

    default boolean isValid(V validated, ConstraintValidatorContext context) {
        return steps().stream()
                .filter($-> $.accept(validated))
                .map($-> $.doValidate(validated, context))
                .filter($-> Boolean.FALSE.equals($))
                .findAny().orElse(true);
    }

}
