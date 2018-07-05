package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.Utils.safeEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author leodavison
 *
 */
public class PhoneNumber {
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_NUMBER = "number";

    @JsonIgnore
    private UUID contactId;
    
    @NotNull(message="{VER0002}")
    @Pattern(regexp="\\w{1,32}", message="{VER0102}")
    private final String type;

    @NotNull(message="{VER0003}")
    @Pattern(regexp="\\d{1,32}", message="{VER0103}")
    private String number;
    
    protected PhoneNumber() {
        // required for morphia
        type = null; //NOSONAR
        number = null; //NOSONAR
    }

    @JsonCreator
    public PhoneNumber(@JsonProperty("type") String type, @JsonProperty("number") String number) {
        this.type = type;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }
    
    public Map<String, String> getLinks() {
        Map<String,String> links = new HashMap<>();
        
        if (contactId != null) {
            links.put("contact", "/api/v1/contacts/" + contactId.toString());
        }
        
        return links;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        PhoneNumber other = (PhoneNumber) obj;
        if (!safeEquals(type, other.type)) {
            return false;
        }

        return safeEquals(number, other.number);
    }

    @Override
    public String toString() {
        return "PhoneNumber [type=" + type + ", number=" + number + "]";
    }
}
