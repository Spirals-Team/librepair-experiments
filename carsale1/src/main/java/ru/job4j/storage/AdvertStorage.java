package ru.job4j.storage;

import ru.job4j.config.AppContext;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.User;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AdvertStorage implements StorageDAO<Advert> {
    private final AdvertDataRepository repository = new AppContext().getContext().getBean(AdvertDataRepository.class);

    @Override
    public Advert add(Advert entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<Advert> getAll() {
        return (List<Advert>) repository.findAll();
    }

    @Override
    public Advert findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void del(Advert entity) {
        repository.delete(entity);
    }

    @Override
    public void del(int id) {
        repository.deleteById(id);
    }

    public List<Advert> findByUser(User user) {
        return repository.findByUser(user);
    }

    public List<Advert> findByBrand(Brand brand) {
        return repository.findByBrand(brand);
    }
}
