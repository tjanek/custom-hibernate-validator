package pl.tjanek.web.test;

import pl.tjanek.web.test.impl.CustomConstraintValidator;
import pl.tjanek.web.test.impl.CustomValidator;
import pl.tjanek.web.test.impl.ValidationStrategy;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static pl.tjanek.web.test.impl.CustomValidator.ConstraintViolation.violate;

public class ValidIdentityDocumentValidator
        implements CustomConstraintValidator<ValidIdentityDocument, IdentityDocumentJson> {

    @Override
    public List<CustomValidator<IdentityDocumentJson>> steps() {
        return Arrays.asList(
                new DocumentIdentityCardNumberValidator(),
                new DocumentIdentityDateValidator(),
                new DocumentIdentityRequiredValidator());
    }

    static class DocumentIdentityCardNumberValidator implements CustomValidator<IdentityDocumentJson> {

        @Override
        public boolean accept(IdentityDocumentJson validated) {
            return validated.getIdentityDocumentType() == IdentityDocumentType.ID_CARD;
        }

        @Override
        public ValidationStrategy<IdentityDocumentJson> validatedBy() {
            return (IdentityDocumentJson validated, ConstraintValidatorContext context) -> false;
        }

        @Override
        public ConstraintViolation constraintViolation() {
            return violate("A", "cardNumber");
        }
    }

    static class DocumentIdentityDateValidator implements CustomValidator<IdentityDocumentJson> {

        @Override
        public boolean accept(IdentityDocumentJson validated) {
            return validated.getIdentityDocumentType() == IdentityDocumentType.ID_CARD;
        }

        @Override
        public ValidationStrategy<IdentityDocumentJson> validatedBy() {
            return (IdentityDocumentJson validated, ConstraintValidatorContext context) -> false;
        }

        @Override
        public ConstraintViolation constraintViolation() {
            return violate("B", "registrationDate");
        }
    }

    static class DocumentIdentityRequiredValidator implements CustomValidator<IdentityDocumentJson> {

        @Override
        public boolean accept(IdentityDocumentJson validated) {
            return validated.getIdentityDocumentType() == IdentityDocumentType.PASSPORT;
        }

        @Override
        public ValidationStrategy<IdentityDocumentJson> validatedBy() {
            return (IdentityDocumentJson validated, ConstraintValidatorContext context) -> false;
        }

        @Override
        public ConstraintViolation constraintViolation() {
            return violate("B", "registrationDate");
        }
    }
}
