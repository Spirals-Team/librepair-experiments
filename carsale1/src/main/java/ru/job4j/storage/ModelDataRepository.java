package ru.job4j.storage;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface ModelDataRepository extends CrudRepository<Model, Integer> {
    List<Model> findByBrand(Brand brand);
}
