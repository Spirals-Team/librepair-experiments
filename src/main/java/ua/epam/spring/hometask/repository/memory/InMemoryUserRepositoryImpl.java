package ua.epam.spring.hometask.repository.memory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.repository.UserRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.domain.DomainObject.START_SEQ;

/**
 * Created by Vladimir on 09.10.2017.
 */

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Long, User> repository = new ConcurrentHashMap<>();

    private AtomicLong counter = new AtomicLong(START_SEQ);

    private static final Comparator<User> USER_COMPARATOR = Comparator
            .comparing(User::getEmail)
            .thenComparing(User::getFirstName)
            .thenComparing(User::getLastName);

    @Override
    public User save(User user) {
        Objects.requireNonNull(user);
        if (user.isNew()) {
            user.setId(counter.getAndIncrement());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(User user) {
        User deletedUser = repository.remove(user.getId());
        return deletedUser != null;
    }

    @Override
    public User get(long id) {
        return repository.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return repository.values().stream().sorted(USER_COMPARATOR).collect(Collectors.toList());
    }
}
