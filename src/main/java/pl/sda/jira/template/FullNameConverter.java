package pl.sda.jira.template;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FullNameConverter implements AttributeConverter<FullName, String> {
    private static final String DELLMITER = ";";

    @Override
    public String convertToDatabaseColumn(FullName fullName) {
        if (fullName == null) {
            return null;
        }
        return fullName.getName() + DELLMITER + fullName.getLastName();
    }

    @Override
    public FullName convertToEntityAttribute(String value) {
        String[] split = value.split(DELLMITER);
        return new FullName(split[0], split[1]);
    }
}
