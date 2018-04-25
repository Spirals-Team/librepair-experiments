package ar.edu.undav.semillero.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Alejandro Gomez
 */
public class CreateStudentRequest extends BaseRequest {

    @NotEmpty
    @Size(max = 255)
    private final String name;
    @NotEmpty
    @Size(max = 255)
    private final String lastName;
    @NotNull
    private final Long node;
    private final LocalDate graduationDate;
    @NotNull
    private final LocalDate courseDate;
    @Email
    @Size(max = 255)
    private final String email;
    @Size(max = 255)
    private final String phone;
    @Size(max = 255)
    private final String resumeUrl;
    private final boolean working;
    private final boolean lookingForWork;
    private final String feedback;

    public CreateStudentRequest(@JsonProperty("name") String name,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("node") Long node,
            @JsonProperty("graduationDate") LocalDate graduationDate,
            @JsonProperty("courseDate") LocalDate courseDate,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone,
            @JsonProperty("resumeUrl") String resumeUrl,
            @JsonProperty("working") boolean working,
            @JsonProperty("lookingForWork") boolean lookingForWork,
            @JsonProperty("feedback") String feedback) {
        this.name = name;
        this.lastName = lastName;
        this.node = node;
        this.graduationDate = graduationDate;
        this.courseDate = courseDate;
        this.email = email;
        this.phone = phone;
        this.resumeUrl = resumeUrl;
        this.working = working;
        this.lookingForWork = lookingForWork;
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public Long getNode() {
        return node;
    }

    public String getEmail() {
        return email;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public LocalDate getCourseDate() {
        return courseDate;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isLookingForWork() {
        return lookingForWork;
    }

    public boolean isWorking() {
        return working;
    }

    public String getFeedback() {
        return feedback;
    }
}
