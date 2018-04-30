package service;

import org.springframework.transaction.annotation.Transactional;
import persistence.GenericRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariano on 14/04/18.
 */
public class GenericService<T> implements Serializable {

    private static final long serialVersionUID = -6540963495078524186L;

    private GenericRepository<T> repository;


    public GenericRepository<T> getRepository() {
        return repository;
    }

    public void setRepository(GenericRepository<T> repository) {
        this.repository = repository;
    }



    @Transactional
    public void delete(final T object) {
        this.getRepository().delete(object);
    }

    @Transactional(readOnly = true)
    public List<T> retriveAll() {
        return this.getRepository().findAll();
    }

    @Transactional
    public void save(final T object) {
        this.getRepository().save(object);
    }

    @Transactional
    public void update(final T object) {
        this.getRepository().update(object);
    }



}
