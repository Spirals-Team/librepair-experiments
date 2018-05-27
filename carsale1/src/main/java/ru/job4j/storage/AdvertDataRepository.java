package ru.job4j.storage;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.User;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface AdvertDataRepository extends CrudRepository<Advert, Integer> {
    List<Advert> findByUser(User user);
    List<Advert> findByBrand(Brand brand);
}
