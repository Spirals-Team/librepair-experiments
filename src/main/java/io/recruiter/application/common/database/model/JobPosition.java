package io.recruiter.application.common.database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobposition")
public class JobPosition {

    @Id
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean active;

    public JobPosition() {
    }

    public JobPosition(String name, boolean active) {
        this.name = name;
        this.active = active;
    }
}
