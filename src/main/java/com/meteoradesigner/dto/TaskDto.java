package com.meteoradesigner.dto;

import com.meteoradesigner.model.InternalExecutionState;
import com.meteoradesigner.model.SelfCompletionState;
import com.meteoradesigner.model.TaskMetric;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * This @code{TaskDto} class declares data transfer object for @code{Task} class.
 */
@Setter
@Getter
@NoArgsConstructor
public class TaskDto extends AbstractNamedDto {

    @NotBlank
    private Integer userId;

    @Size(max = 6400000)
    private String description;

    @NotNull
    private LocalDateTime plannedStartTaskTimestamp;

    @NotNull
    private LocalDateTime plannedStopTaskTimestamp;

    private LocalDateTime actualStartTaskTimestamp = LocalDateTime.MAX;

    private LocalDateTime actualStopTaskTimestamp = LocalDateTime.MAX;

    private Integer portfolioId;

    private TaskMetric metrics;

    @NotNull
    private InternalExecutionState internalExecutionState;

    @NotNull
    private SelfCompletionState selfCompletionState;

    /**
     * The all-args constructor.
     */
    public TaskDto(Integer idToSet,
                   String displayNameToSet,
                   Integer userIdToSet,
                   String descriptionToSet,
                   LocalDateTime plannedStartTaskTimestampToSet,
                   LocalDateTime plannedStopTaskTimestampToSet,
                   LocalDateTime actualStartTaskTimestampToSet,
                   LocalDateTime actualStopTaskTimestampToSet,
                   Integer taskPortfolioIdToSet,
                   TaskMetric taskMetricToSet
    ) {
        super(idToSet, displayNameToSet);
        this.userId = userIdToSet;
        this.description = descriptionToSet;
        this.plannedStartTaskTimestamp = plannedStartTaskTimestampToSet;
        this.plannedStopTaskTimestamp = plannedStopTaskTimestampToSet;
        this.actualStartTaskTimestamp = actualStartTaskTimestampToSet;
        this.actualStopTaskTimestamp = actualStopTaskTimestampToSet;
        this.portfolioId = taskPortfolioIdToSet;
        this.metrics = taskMetricToSet;
    }
}