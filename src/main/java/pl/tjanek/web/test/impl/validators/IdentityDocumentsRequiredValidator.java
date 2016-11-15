package pl.tjanek.web.test.impl.validators;

import pl.tjanek.web.CarJson;
import pl.tjanek.web.test.IdentityDocumentJson;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.tjanek.web.test.IdentityDocumentType.ID_CARD;
import static pl.tjanek.web.test.impl.validators.IdentityDocumentsRequiredValidator.ValidationMessage.error;

public class IdentityDocumentsRequiredValidator
        implements ConstraintValidator<IdentityDocumentsRequired, CarJson> {

    public static final String REQUIRED_TEMPLATE = "Required";

    private List<ValidationMessage> validationMessages = new ArrayList<>();
    private List<ValidationStrategy> validationStrategies = new ArrayList<>();

    @Override
    public void initialize(IdentityDocumentsRequired constraintAnnotation) {
        validationStrategies = Arrays.asList(
            new MainIdentityDocumentRequired(),
            new IdCardIdentityDocumentRequired()
        );
    }

    @Override
    public boolean isValid(CarJson value, ConstraintValidatorContext context) {
        validationStrategies.stream()
                .filter($-> $.accept(value))
                .forEach($-> $.validate(value, validationMessages));
        boolean isValid = validationMessages.isEmpty();
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            validationMessages.forEach($-> {
                context.buildConstraintViolationWithTemplate($.message)
                        .addPropertyNode($.key)
                        .addConstraintViolation();
            });
        }
        return isValid;
    }

    interface ValidationStrategy {
        boolean accept(CarJson carJson);
        void validate(CarJson carJson, List<ValidationMessage> validationMessages);

        default String path(String documentProperty, String property) {
            return String.format("%s.%s", documentProperty, property);
        }
    }

    static class MainIdentityDocumentRequired implements ValidationStrategy {

        @Override
        public boolean accept(CarJson carJson) {
            return carJson.getMainDocument() == null;
        }

        @Override
        public void validate(CarJson carJson, List<ValidationMessage> validationMessages) {
            validationMessages.add(error(path("mainDocument", "identityDocumentType"), REQUIRED_TEMPLATE));
        }
    }

    static class IdCardIdentityDocumentRequired implements ValidationStrategy {

        @Override
        public boolean accept(CarJson carJson) {
            return idCardSelected(carJson.getMainDocument()) ||
                   idCardSelected(carJson.getAdditionalDocument());
        }

        @Override
        public void validate(CarJson carJson, List<ValidationMessage> validationMessages) {
            IdentityDocumentJson mainDocument = carJson.getMainDocument();
            IdentityDocumentJson additionalDocument = carJson.getAdditionalDocument();
            doValidate(mainDocument, "mainDocument", validationMessages, carJson);
            doValidate(additionalDocument, "additionalDocument", validationMessages, carJson);
        }

        private void doValidate(IdentityDocumentJson document, String documentProperty, List<ValidationMessage> validationMessages, CarJson carJson) {
            if (document == null) {
                return;
            }
            if (cardNumberIsRequired(document)) {
                validationMessages.add(error(path(documentProperty, "cardNumber"), REQUIRED_TEMPLATE));
            }
            if (dateOfIssueIsRequired(document)) {
                validationMessages.add(error(path(documentProperty, "registrationDate"), REQUIRED_TEMPLATE));
            }
            if (expiryDateIsRequired(document, carJson)) {
                validationMessages.add(error(path(documentProperty, "endDate"), REQUIRED_TEMPLATE));
            }
        }

        private boolean idCardSelected(IdentityDocumentJson document) {
            return document != null && document.getIdentityDocumentType() == ID_CARD;
        }

        private boolean expiryDateIsRequired(IdentityDocumentJson document, CarJson carJson) {
            return document.getEndDate() == null && document.getIdentityDocumentType() != null && isOver65Age(carJson);
        }

        private boolean dateOfIssueIsRequired(IdentityDocumentJson document) {
            return document.getRegistrationDate() == null && document.getIdentityDocumentType() != null;
        }

        private boolean cardNumberIsRequired(IdentityDocumentJson document) {
            return document.getCardNumber() == null && document.getIdentityDocumentType() != null;
        }

        private boolean isOver65Age(CarJson carJson) {
            String pesel = carJson.getPesel();
            return new Pesel(pesel).isOlderThan(65);
        }
    }

    static class ValidationMessage {
        String key;
        String message;

        public ValidationMessage(String key, String message) {
            this.key = key;
            this.message = message;
        }

        static ValidationMessage error(String key, String message) {
           return new ValidationMessage(key, message);
        }
    }
}
