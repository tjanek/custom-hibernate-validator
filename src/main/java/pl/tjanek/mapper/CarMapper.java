package pl.tjanek.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import pl.tjanek.converter.LocalDateConverter;
import pl.tjanek.domain.Car;
import pl.tjanek.web.CarJson;

public class CarMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new LocalDateConverter());
        factory.classMap(CarJson.class, Car.class)
                .byDefault()
                .register();

    }
}
