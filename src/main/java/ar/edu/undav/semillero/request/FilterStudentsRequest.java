package ar.edu.undav.semillero.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class FilterStudentsRequest extends BaseRequest {
    private final LocalDate minGraduationDate;
    private final LocalDate maxGraduationDate;
    private final Long node;
    private final boolean graduated;
    private final boolean working;
    private final boolean lookingForWork;

    public FilterStudentsRequest(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @JsonProperty("minGraduationDate")
                    LocalDate minGraduationDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @JsonProperty("maxGraduationDate")
                    LocalDate maxGraduationDate,
            @JsonProperty("node")
                    Long node,
            @JsonProperty("graduated")
                    Boolean graduated,
            @JsonProperty("working")
                    Boolean working,
            @JsonProperty("lookingForWork")
                    Boolean lookingForWork) {
        this.minGraduationDate = minGraduationDate;
        this.maxGraduationDate = maxGraduationDate;
        this.node = node;
        this.graduated = graduated != null && graduated;
        this.working = working != null && working;
        this.lookingForWork = lookingForWork != null && lookingForWork;
    }

    public LocalDate getMinGraduationDate() {
        return minGraduationDate;
    }

    public LocalDate getMaxGraduationDate() {
        return maxGraduationDate;
    }

    public Long getNode() {
        return node;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public boolean isWorking() {
        return working;
    }

    public boolean isLookingForWork() {
        return lookingForWork;
    }
}
