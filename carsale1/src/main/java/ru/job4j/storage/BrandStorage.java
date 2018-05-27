package ru.job4j.storage;

import ru.job4j.config.AppContext;
import ru.job4j.models.Brand;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BrandStorage implements StorageDAO<Brand> {
    private final BrandDataRepository repository = new AppContext().getContext().getBean(BrandDataRepository.class);

    @Override
    public Brand add(Brand entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Brand> getAll() {
        return (List<Brand>) repository.findAll();
    }

    @Override
    public Brand findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void del(Brand entity) {
        repository.delete(entity);
    }

    @Override
    public void del(int id) {
        repository.deleteById(id);
    }
}
