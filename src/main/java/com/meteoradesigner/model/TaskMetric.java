package com.meteoradesigner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.io.Serializable;

//TODO fix all the documentation, by using this class, -es and dots.
/**
 * TaskMetric - not the entity, default parameters set for a task.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskMetric implements Serializable {

    @Size(max = 6400)
    private String description;

    @Size(min = -100, max = 100)
    private Double importance;

    @Size(min = -100, max = 100)
    private Double urgency;

    @Size(min = -100, max = 100)
    private Double stability;

    /**
     * Copy constructor.
     */
    public TaskMetric(TaskMetric taskMetricToCopy) {
        this(taskMetricToCopy.getDescription(), taskMetricToCopy.getImportance(),
                taskMetricToCopy.getUrgency(), taskMetricToCopy.getStability());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TaskMetric{" +
                "description='" + description + '\'' +
                ", importance=" + importance +
                ", urgency=" + urgency +
                ", stability=" + stability +
                '}';
    }
}
