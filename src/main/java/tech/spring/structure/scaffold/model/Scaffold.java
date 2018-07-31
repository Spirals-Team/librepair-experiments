package tech.spring.structure.scaffold.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Scaffold {

    private String name;

    @JsonIgnore
    private String authorization;

    private List<Property> properties;

    public Scaffold() {
        properties = new ArrayList<Property>();
    }

    public Scaffold(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public static Scaffold of(String name) {
        return new Scaffold(name);
    }

}
