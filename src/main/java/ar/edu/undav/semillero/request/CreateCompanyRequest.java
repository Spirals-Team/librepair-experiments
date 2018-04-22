package ar.edu.undav.semillero.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Alejandro Gomez
 */
public class CreateCompanyRequest extends BaseRequest {

    @NotEmpty
    @Size(max = 255)
    private final String name;
    @NotEmpty
    @Size(max = 255)
    private final String contact;
    @NotEmpty
    @Email
    @Size(max = 255)
    private final String email;
    @Size(max = 1000)
    private final String comments;

    public CreateCompanyRequest(@JsonProperty("name") String name, @JsonProperty("contact") String contact, @JsonProperty("email") String email, @JsonProperty("comments") String comments) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getComments() {
        return comments;
    }
}
