package pl.tjanek.validation;

import pl.tjanek.web.CarJson;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCarModelNameValidator
        implements ConstraintValidator<ValidCarModelName, CarJson> {

    @Override
    public void initialize(ValidCarModelName constraintAnnotation) {
    }

    @Override
    public boolean isValid(CarJson car, ConstraintValidatorContext context) {
        if (!car.nissan()) {
            violationOf("Only Nissan cars are allowed to create", "model.name", context);
            return false;
        }
        return true;
    }

    private void violationOf(String template, String property, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template)
                .addPropertyNode(property).addConstraintViolation();
    }
}
