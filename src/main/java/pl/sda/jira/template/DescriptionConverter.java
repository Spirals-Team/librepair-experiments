package pl.sda.jira.template;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DescriptionConverter implements AttributeConverter<Description, String> {
    @Override
    public String convertToDatabaseColumn(Description description) {
        if (description == null) {
            return null;
        }
        return description.value();
    }

    @Override
    public Description convertToEntityAttribute(String value) {
        return new Description(value);
    }
}
