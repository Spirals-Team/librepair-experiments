package com.meteoradesigner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;


/**
 * Class implements the task portfolio entity.
 */
//TODO fix all the documentation, by using this class, -es and dots.
@Entity
@Table(name = "portfolios", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id",
        "display_name"}, name = "user_id_portfolio_display_name")})
@Getter
@Setter
@NoArgsConstructor
public class TaskPortfolio extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "description")
    @Size(max = 6400)
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "portfolio")
    private Set<Task> tasks;

    /**
     * Deep copy constructor.
     */
    //TODO fix
    public TaskPortfolio(TaskPortfolio taskPortfolio) {
        TaskPortfolio taskPortfolioCopy = SerializationUtils.clone(taskPortfolio);
        new TaskPortfolio(taskPortfolioCopy.getId(), taskPortfolioCopy.getDisplayName(),
                taskPortfolioCopy.getUser(), taskPortfolioCopy.getDescription(),
                taskPortfolioCopy.getTasks());
    }

    /**
     * The minimum parameters initializing constructors.
     */
    public TaskPortfolio(String nameToSet, @NotNull User user, @NotNull @Size
            (min = 1, max = 6400) String description) {
        this(0, nameToSet, user, description, Collections.emptySet());
    }

    /**
     * The all-args constructors.
     */
    public TaskPortfolio(Integer idToSet, String nameToSet, @NotNull User user, @NotNull @Size
            (min = 1, max = 6400) String description, Set<Task> tasks) {
        super(idToSet, nameToSet);
        this.user = user;
        this.description = description;
        this.tasks = tasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TaskPortfolio{" +
                "user=" + user.getDisplayName() +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}