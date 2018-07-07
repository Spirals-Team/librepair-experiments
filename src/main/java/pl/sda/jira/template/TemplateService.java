package pl.sda.jira.template;

public class TemplateService {
    private final TemplateRepository repository;

    public TemplateService(TemplateRepository repository) {
        this.repository = repository;
    }
}
