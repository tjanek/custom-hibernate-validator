package pl.tjanek.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class CarEndpoint {

    @PostMapping(value = "/car", consumes = APPLICATION_JSON_UTF8_VALUE)
    public void create(@Valid @RequestBody CarJson car) throws URISyntaxException {

    }
}
