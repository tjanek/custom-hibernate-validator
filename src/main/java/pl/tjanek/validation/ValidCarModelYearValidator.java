package pl.tjanek.validation;

import pl.tjanek.web.CarJson;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCarModelYearValidator
        implements ConstraintValidator<ValidCarModelYear, CarJson> {

    @Override
    public void initialize(ValidCarModelYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(CarJson car, ConstraintValidatorContext context) {
        if (car.modelYear() < 2016) {
            violationOf("Only cars from 2016 and above are allowed to create", "model.year", context);
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
