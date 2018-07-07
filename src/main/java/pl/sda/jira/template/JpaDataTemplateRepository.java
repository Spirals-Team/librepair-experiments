package pl.sda.jira.template;

import org.springframework.stereotype.Repository;

@Repository
public class JpaDataTemplateRepository implements TemplateRepository {
    private final CrudJpaTemplateRepository repository;

    public JpaDataTemplateRepository(CrudJpaTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Template getBy(String id) {
        return repository.findOne(Long.valueOf(id));
    }

    @Override
    public void remove(String id) {
        repository.delete(Long.valueOf(id));
    }

    @Override
    public Template create(Template template) {
        return repository.save(template);
    }

    @Override
    public Template update(Template template) {
        return repository.save(template);
    }
}
