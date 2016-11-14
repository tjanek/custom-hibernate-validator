package pl.tjanek.web.test.impl;

import javax.validation.ConstraintValidatorContext;

public interface CustomValidator<T> {

    boolean accept(T validated);

    default ValidationStrategy<T> validatedBy() {
        return (T validated, ConstraintValidatorContext context) -> true;
    }

    default ConstraintViolation constraintViolation() {
        return null;
    }

    default void violationOf(ConstraintViolation constraintViolation, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(constraintViolation.template)
                .addPropertyNode(constraintViolation.property).addConstraintViolation();
    }

    default boolean doValidate(T validated, ConstraintValidatorContext context) {
        boolean isValid = validatedBy().validate(validated, context);
        ConstraintViolation constraintViolation = constraintViolation();
        if (!isValid && constraintViolation != null) {
            violationOf(constraintViolation, context);
        }
        return isValid;
    }

    class ConstraintViolation {
        String template;
        String property;

        public static ConstraintViolation violate(String template, String property) {
            return new ConstraintViolation(template, property);
        }

        private ConstraintViolation(String template, String property) {
            this.template = template;
            this.property = property;
        }
    }

}
