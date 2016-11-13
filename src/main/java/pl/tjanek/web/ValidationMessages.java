package pl.tjanek.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ValidationMessages {

    private List<ValidationMessage> messages;

    public ValidationMessages() {
    }

    public ValidationMessages(List<ObjectError> errors) {
        this.messages = errors.stream()
                .map(ValidationMessage::new)
                .collect(Collectors.toList());
    }

    public List<ValidationMessage> getMessages() {
        return messages;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    static class ValidationMessage {
        String fieldName;
        String message;

        public ValidationMessage() {
        }

        public ValidationMessage(ObjectError error) {
            FieldError fieldError = (FieldError) error;
            this.fieldName = fieldError.getField();
            this.message = fieldError.getDefaultMessage();
        }

    }
}
