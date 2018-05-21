package edu.itu.cavabunga.core.http;

import edu.itu.cavabunga.core.entity.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Response object that contains requested components in data field
 * @see Component
 */
@Getter
@Setter
public class ComponentResponse extends Response {

    /**
     * list of requested components
     */
    private List<Component> data = new ArrayList <>();

    /**
     * Response object constructor that contains multiple components in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response components
     */
    public ComponentResponse(Integer code, String message, List<Component> data) {
        super(code, message);
        this.data = data;
    }

    /**
     * Response object constructor that contains single component in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response component
     */
    public ComponentResponse(Integer code, String message, Component data) {
        super(code, message);
        this.data.add(data);
    }
}
