package pl.tjanek.domain;

import java.io.Serializable;

public class CarModel implements Serializable {

    private String name;
    private int year;

    public CarModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
