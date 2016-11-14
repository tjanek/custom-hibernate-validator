package pl.tjanek.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.util.StringUtils.hasText;

@Configuration
public class JacksonConfiguration {

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = ofPattern("dd-MM-yyyy");

    @Bean
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializers)
                throws IOException {
            generator.writeString(value.format(LOCAL_DATE_FORMATTER));
        }
    }

    static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctx)
                throws IOException {
            String valueAsString = p.getValueAsString();
            return (hasText(valueAsString)) ?
                    LocalDate.parse(valueAsString, LOCAL_DATE_FORMATTER) :
                    null;
        }
    }

}
