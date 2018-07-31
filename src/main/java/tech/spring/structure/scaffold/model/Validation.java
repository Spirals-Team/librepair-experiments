package tech.spring.structure.scaffold.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Validation {

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object value;

    private String message;

    public Validation() {

    }

    public Validation(String name, String message) {
        this();
        this.name = name;
        this.message = message;
    }

    public Validation(String name, Object value, String message) {
        this(name, message);
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Validation of(String name, String message) {
        return new Validation(name, message);
    }

    public static Validation of(String name, Object value, String message) {
        return new Validation(name, value, message);
    }

}
