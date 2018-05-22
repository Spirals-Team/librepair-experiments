package org.luksze;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    public final String firstName;
    public final String secondName;

    Person(@JsonProperty("firstName") String firstName,
           @JsonProperty("secondName") String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
