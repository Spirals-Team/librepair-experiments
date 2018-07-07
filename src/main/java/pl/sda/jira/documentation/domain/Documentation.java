package pl.sda.jira.documentation.domain;

import pl.sda.jira.documentation.dto.DocumentationDto;

public class Documentation {
    private Long id;
    private String title;

    public Documentation(Long id , String name) {
        this.id = id;
        this.title = name;

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DocumentationDto asDto() {
        return new DocumentationDto(title);
    }

    public void setNewName(String title) {
        this.title = title;

    }
}
