package ru.job4j.storage;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.Brand;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface BrandDataRepository extends CrudRepository<Brand, Integer> {
}
