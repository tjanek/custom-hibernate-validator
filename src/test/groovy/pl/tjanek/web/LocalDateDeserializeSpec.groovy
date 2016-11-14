package pl.tjanek.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8080")
class LocalDateDeserializeSpec extends Specification {

    @Autowired
    ObjectMapper jsonMapper;

    @Unroll
    def "Should treat '#value' date value as '#expected' after deserialization"() {
        given:
        def json = "{\"registrationDate\": " + date_value(value) + "}"

        when:
        def car = jsonMapper.readValue(json, CarJson.class)

        then:
        car.registrationDate == expected

        where:
        value        | expected
        ''           | null
        '  '         | null
        null         | null
        '05-11-2016' | LocalDate.of(2016, 11, 5)
    }

    def date_value(val) {
        (val == null) ? "null" : "\"" + val + "\""
    }
}
