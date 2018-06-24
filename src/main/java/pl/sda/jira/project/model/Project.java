package pl.sda.jira.project.model;

public class Project {

    private Long id;
    private String name;

    public Project(Long id,ProjectDto projectDto) {
        this.id = id;
        this.name=projectDto.getName();
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


