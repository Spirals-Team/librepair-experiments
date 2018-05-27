package ru.job4j.storage;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.User;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface UserDataRepository extends CrudRepository<User, Integer> {
}
