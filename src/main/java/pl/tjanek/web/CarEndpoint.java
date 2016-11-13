package pl.tjanek.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tjanek.domain.Car;
import pl.tjanek.mapper.CarMapper;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class CarEndpoint {

    @PostMapping(value = "/car", consumes = APPLICATION_JSON_UTF8_VALUE)
    public void create(@Valid @RequestBody CarJson car) {
        Car newCar = new CarMapper().map(car, Car.class);
    }
}
