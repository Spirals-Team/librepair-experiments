package ar.edu.undav.semillero.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Alejandro Gomez
 */
public class CreateInterviewRequest extends BaseRequest {

    @NotNull
    private final Long student;
    @NotNull
    private final Long company;

    public CreateInterviewRequest(@JsonProperty("student") Long student, @JsonProperty("company") Long company) {
        this.student = student;
        this.company = company;
    }

    public Long getStudent() {
        return student;
    }

    public Long getCompany() {
        return company;
    }
}
