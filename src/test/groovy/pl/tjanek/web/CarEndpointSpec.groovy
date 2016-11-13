package pl.tjanek.web

import com.jayway.restassured.RestAssured
import com.jayway.restassured.parsing.Parser
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDate

import static com.jayway.restassured.RestAssured.given

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8080")
class CarEndpointSpec extends Specification {

    def setup() {
        RestAssured.port = 8080
        RestAssured.defaultParser = Parser.JSON
    }

    def "Should create a new car"() {
        given:
        def car = aCar()
        car.model.name = model_name
        car.model.year = model_year

        when:
        def response = given()
                .contentType("application/json;charset=UTF-8")
                .body(car)
                .when()
                .post('car')
                .thenReturn()

        then:
        response.statusCode == status_code

        where:
        status_code | model_name | model_year
        200         | 'Nissan'   | 2016
    }


    def "Should fail when create with custom validation messages"() {
        given:
        def car = aCar()
        car.model.name = model_name
        car.model.year = model_year

        when:
        def response = given()
                .contentType("application/json;charset=UTF-8")
                .body(car)
                .when()
                .post('car')
                .thenReturn()

        then:
        response.statusCode == status_code
        def validation = response.as(ValidationMessages)
        validation.messages[0].message == validation_message
        validation.messages[0].fieldName == field_name

        where:
        validation_message                                    | field_name   | status_code | model_name | model_year
        'Only cars from 2016 and above are allowed to create' | 'model.year' | 422         | 'Nissan'   | 2013
        'Only Nissan cars are allowed to create'              | 'model.name' | 422         | 'Mazda'    | 2016
    }

    private CarJson aCar() {
        new CarJson(LocalDate.of(2016, 10, 01), new CarModelJson())
    }

}
