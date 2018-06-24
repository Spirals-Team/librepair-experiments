package pl.sda.jira.documentation.domain;

public class Documentation {
    private Long id;
    private String name;

    public Documentation(Long id , String name) {
        this.id = id;
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
