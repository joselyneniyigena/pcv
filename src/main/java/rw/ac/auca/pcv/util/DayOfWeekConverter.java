package rw.ac.auca.pcv.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.DayOfWeek;

@Converter(autoApply = true)
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DayOfWeek day) {
        if (day == null) {
            return null;
        }
        return day.getValue();
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return DayOfWeek.of(value);
    }
}
