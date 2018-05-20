package edu.itu.cavabunga.core.entity.property;

public enum PropertyValueType {
    BINARY("BINARY"),
    BOOLEAN("BOOLEAN"),
    CALADDRESS("CAL-ADDRESS"),
    DATE("DATE"),
    DATETIME("DATE-TIME"),
    DURATION("DURATION"),
    FLOAT("FLOAT"),
    INTEGER("INTEGER"),
    PERIOD("PERIOD"),
    RECUR("RECUR"),
    TEXT("TEXT"),
    TIME("TIME"),
    URI("URI"),
    UTCOFFSET("UTC-OFFSET");

    private final String value;

    PropertyValueType(final String text){
        this.value = text;
    }

    @Override
    public String toString(){
        return value;
    }
}
