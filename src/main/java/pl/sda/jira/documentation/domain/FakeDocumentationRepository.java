package pl.sda.jira.documentation.domain;



public class FakeDocumentationRepository implements DocumentationRepository {
    private boolean somethingAdded = false;

    public void add(Documentation documentation) {
        somethingAdded = true;
    }

    public boolean existForProject() {
        return somethingAdded;
    }
}
