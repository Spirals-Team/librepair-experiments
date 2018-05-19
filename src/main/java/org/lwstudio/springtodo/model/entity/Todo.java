package org.lwstudio.springtodo.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Todo implements Serializable {
    private static final long serialVersionUID = 6505147293149474785L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String description;

    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCompletedAt() { return completedAt; }

    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public Date getCreatedAt() { return createdAt; }

}
