package ar.edu.undav.semillero.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

/**
 * @author Alejandro Gomez
 */
public class CreateNodeRequest extends BaseRequest {

    @NotEmpty
    private final String name;
    @NotEmpty
    private final String address;

    public CreateNodeRequest(@JsonProperty("name") String name, @JsonProperty("address") String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
