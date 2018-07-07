package pl.sda.jira.template;

public interface TemplateRepository {
    Template getBy(String id);
    void remove(String id);
    Template create(Template template);
    Template update(Template template);
}
