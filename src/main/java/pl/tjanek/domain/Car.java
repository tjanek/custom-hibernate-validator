package pl.tjanek.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Car implements Serializable {

    private LocalDate registrationDate;

    private CarModel model;

    public Car() {
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public CarModel getModel() {
        return model;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }
}
