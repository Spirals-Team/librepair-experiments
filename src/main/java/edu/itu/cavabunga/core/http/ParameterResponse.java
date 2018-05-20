package edu.itu.cavabunga.core.http;

import edu.itu.cavabunga.core.entity.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object that contains requested parameters in data field
 * @see Parameter
 */
@Getter
@Setter
public class ParameterResponse extends Response {

    /**
     * list of requested parameters
     */
    private List<Parameter> data = new ArrayList <>();

    /**
     * Response object constructor that contains multiple parameters in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response parameters
     */
    public ParameterResponse(Integer code, String message, List<Parameter> data) {
        super(code, message);
        this.data = data;
    }

    /**
     * Response object constructor that contains single parameter in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response parameter
     */
    public ParameterResponse(Integer code, String message, Parameter data) {
        super(code, message);
        this.data.add(data);
    }
}
