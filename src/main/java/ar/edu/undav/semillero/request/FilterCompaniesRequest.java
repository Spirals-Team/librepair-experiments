package ar.edu.undav.semillero.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alejandro Gomez
 */
public class FilterCompaniesRequest extends BaseRequest {

    private final String name;
    private final boolean workers;
    private final boolean interviews;

    public FilterCompaniesRequest(@JsonProperty("name") String name, @JsonProperty("workers") Boolean workers, @JsonProperty("interviews") Boolean interviews) {
        this.name = name;
        this.workers = workers != null && workers;
        this.interviews = interviews != null && interviews;
    }

    public String getName() {
        return name;
    }

    public boolean isWorkers() {
        return workers;
    }

    public boolean isInterviews() {
        return interviews;
    }
}
