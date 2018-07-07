package pl.sda.jira.template;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTemplateRepository implements TemplateRepository {
    private final Map<String, Template> templates = new HashMap<>();

    @Override
    public Template getBy(String id) {
        return templates.get(id);
    }

    @Override
    public void remove(String id) {

    }

    @Override
    public Template create(Template template) {
        return null;
    }

    @Override
    public Template update(Template template) {
        return null;
    }
}
