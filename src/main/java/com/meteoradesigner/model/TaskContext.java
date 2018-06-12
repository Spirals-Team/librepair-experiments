package com.meteoradesigner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

/**
 * Class implements task context entity.
 */
//TODO fix all the documentation, by using this class, -es and dots.
@Entity
@Table(name = "contexts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id",
                                         "display_name"},
                          name = "user_id_context_display_name")})
@Getter
@Setter
@NoArgsConstructor
public class TaskContext extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "description")
    @Size(max = 6400)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                mappedBy = "contexts")
    private Set<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                mappedBy = "internalContexts")
    private Set<TaskContext> externalContexts;

    //TODO check save and delete
    //TODO table with columns?!
    @ManyToMany(fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "external_contexts_internal_contexts",
               joinColumns = {@JoinColumn(
                       name = "external_context_id",
                       referencedColumnName = "id",
                       nullable = false)},
               inverseJoinColumns = {@JoinColumn(
                       name = "internal_context_id",
                       referencedColumnName = "id",
                       nullable = false)})
    private Set<TaskContext> internalContexts;

    /**
     * The deep copying constructor.
     */
    //TODO fix
    public TaskContext(TaskContext taskContextToCopy) {
        TaskContext taskContextCopy = SerializationUtils.clone(taskContextToCopy);
        new TaskContext(taskContextCopy.getId(), taskContextCopy.getDisplayName(), taskContextCopy
                .getUser(), taskContextCopy.getDescription(), taskContextCopy.getExternalContexts(),
                        taskContextCopy.getInternalContexts(), taskContextCopy.getTasks());
    }

    /**
     * The minimal parameters initializing constructor.
     */
    public TaskContext(Integer idToSet,
                       String nameToSet,
                       @NotNull User user,
                       @NotNull @Size(
                               min = 1, max = 6400) String description) {
        this(idToSet, nameToSet, user, description, Collections.emptySet(), Collections.emptySet(),
             Collections.emptySet());
    }

    /**
     * The all-args constructor.
     */
    public TaskContext(Integer idToSet,
                       String nameToSet,
                       @NotNull User user,
                       @NotNull @Size(
                               min = 1, max = 6400) String description,
                       Set<TaskContext> externalContexts,
                       Set<TaskContext> internalContexts,
                       Set<Task> tasks) {
        super(idToSet, nameToSet);
        this.user = user;
        this.description = description;
        this.externalContexts = externalContexts;
        this.internalContexts = internalContexts;
        this.tasks = tasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TaskContext{" +
                "id=" + getId() +
                "user=" + user.getDisplayName() +
                ", description='" + description +
                '}';
    }
}