package pl.sda.jira.documentation.dto;

public class DocumentationDto {
    private String title;

    public DocumentationDto() {
    }

    public DocumentationDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

}
