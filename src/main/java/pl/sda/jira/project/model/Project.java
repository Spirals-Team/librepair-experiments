package pl.sda.jira.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Project {
@Id
@GeneratedValue
    private Long id;
    private String name;

    public Project(ProjectDto projectDto) {
        this.name=projectDto.getName();
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProjectDto asDto() {
        return new ProjectDto(getName());
    }

    public void update(ProjectDto projectDto) {
        this.name=projectDto.getName();
    }
}


