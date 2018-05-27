package ru.job4j.storage;

import ru.job4j.config.AppContext;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ModelStorage implements StorageDAO<Model> {
    private final ModelDataRepository repository = new AppContext().getContext().getBean(ModelDataRepository.class);

    @Override
    public Model add(Model entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Model> getAll() {
        return (List<Model>) repository.findAll();
    }

    @Override
    public Model findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void del(Model entity) {
        repository.delete(entity);
    }

    @Override
    public void del(int id) {
        repository.deleteById(id);
    }

    public List<Model> findByBrand(Brand brand) {
        return repository.findByBrand(brand);
    }
}
