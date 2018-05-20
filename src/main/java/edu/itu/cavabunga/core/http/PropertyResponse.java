package edu.itu.cavabunga.core.http;

import edu.itu.cavabunga.core.entity.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object that contains requested properties in data field
 * @see Property
 */
@Getter
@Setter
public class PropertyResponse extends Response {

    /**
     * list of requested properties
     */
    private List<Property> data = new ArrayList <>();

    /**
     * Response object constructor that contains multiple properties in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response properties
     */
    public PropertyResponse(Integer code, String message, List<Property> data) {
        super(code, message);
        this.data = data;
    }

    /**
     * Response object constructor that contains single property in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response property
     */
    public PropertyResponse(Integer code, String message, Property data) {
        super(code, message);
        this.data.add(data);
    }
}
