package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
public enum PropertyType {
    STRING("string"),

    NUMBER("number"),

    BOOLEAN("boolean"),

    OBJECT("object"),

    INTEGER("integer");

    private String value;

    PropertyType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PropertyType fromValue(String text) {
        for (PropertyType b : PropertyType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }

}
