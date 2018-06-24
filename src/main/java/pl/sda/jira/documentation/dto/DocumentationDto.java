package pl.sda.jira.documentation.dto;

public class DocumentationDto {
    private Long id;
    private String title;


    public DocumentationDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
