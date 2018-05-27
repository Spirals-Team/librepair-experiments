package pl.sda.jira.documentation.domain;

public interface DocumentationRepository {
    boolean existForProject();

    void add(Documentation documentation);
}
