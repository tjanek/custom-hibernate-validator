package pl.tjanek.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import pl.tjanek.validation.ValidCarModelName;
import pl.tjanek.validation.ValidCarModelYear;

import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.DEFAULT;

@ValidCarModelYear
@ValidCarModelName
@JsonAutoDetect(fieldVisibility = DEFAULT)
public class CarJson implements Serializable {

    private LocalDate registrationDate;

    private CarModelJson model;

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

}
