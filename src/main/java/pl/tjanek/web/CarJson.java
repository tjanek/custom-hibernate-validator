package pl.tjanek.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import pl.tjanek.validation.ValidCarModelName;
import pl.tjanek.validation.ValidCarModelYear;
import pl.tjanek.web.test.IdentityDocumentJson;
import pl.tjanek.web.test.ValidIdentityDocument;

import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.DEFAULT;

@ValidCarModelYear
@ValidCarModelName
@JsonAutoDetect(fieldVisibility = DEFAULT)
public class CarJson implements Serializable {

    private LocalDate registrationDate;

    private CarModelJson model;

    @ValidIdentityDocument
    private IdentityDocumentJson mainDocument;

    @ValidIdentityDocument
    private IdentityDocumentJson additionalDocument;

    public CarJson() {
    }

    public CarJson(LocalDate registrationDate, CarModelJson model) {
        this.registrationDate = registrationDate;
        this.model = model;
    }

    public int modelYear() {
        return model.getYear();
    }

    public boolean nissan() {
        return "Nissan".equalsIgnoreCase(model.getName());
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public CarModelJson getModel() {
        return model;
    }

    public void setModel(CarModelJson model) {
        this.model = model;
    }

    public IdentityDocumentJson getMainDocument() {
        return mainDocument;
    }

    public void setMainDocument(IdentityDocumentJson mainDocument) {
        this.mainDocument = mainDocument;
    }

    public IdentityDocumentJson getAdditionalDocument() {
        return additionalDocument;
    }

    public void setAdditionalDocument(IdentityDocumentJson additionalDocument) {
        this.additionalDocument = additionalDocument;
    }
}
