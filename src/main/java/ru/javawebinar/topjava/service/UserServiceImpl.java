package ru.javawebinar.topjava.service;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.DuplicateException;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Objects;

@Service
public final class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repo;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        Objects.requireNonNull(repo, "repo");
        this.repo = repo;
    }

    @Override
    public User register(User user) throws DuplicateException {
        Preconditions.checkArgument(user.isNew(), "not new");
        if (user.isAdmin()) {
            throw new InsufficientPrivilegesException("register");
        }
        return repo.add(user);
    }

    @Override
    public void update(User user, User onBehalfOf) throws NotFoundException, DuplicateException {
        log.info("update({}, {})", user, onBehalfOf);
        Preconditions.checkArgument(!user.isNew(), "new");
        User saved = repo.get(user.getId());
        log.debug("saved={}", saved);
        if (!onBehalfOf.isAdmin() && (onBehalfOf.getId() != user.getId() || user.isEnabled() != saved.isEnabled())) {
            throw new InsufficientPrivilegesException("update");
        }
        repo.update(user);
    }

    @Override
    public void remove(int userId, User onBehalfOf) throws NotFoundException {
        log.info("remove({}, {})", userId, onBehalfOf);
        if (!onBehalfOf.isAdmin()) {
            throw new InsufficientPrivilegesException("remove");
        }
        User saved = repo.get(userId);
        if (saved.isAdmin() && repo.atMostOneAdminLeft()) {
            throw new InsufficientPrivilegesException("remove");
        }
        repo.remove(userId);
    }

    @Override
    public User get(int userId, User onBehalfOf) throws NotFoundException {
        User saved = null;
        if (!onBehalfOf.isAdmin()) {
            saved = repo.get(userId);
            if (saved.getId() != onBehalfOf.getId()) {
                assert userId == saved.getId();
                throw new InsufficientPrivilegesException("get");
            }
        }
        return saved == null ? repo.get(userId) : saved;
    }

    @Override
    public User getByEmail(String email, User onBehalfOf) throws NotFoundException {
        log.debug("getByEmail({}, {})", email, onBehalfOf);
        User saved = null;
        if (!onBehalfOf.isAdmin()) {
            saved = repo.getByEmail(email);
            log.debug("saved={}", saved);
            if (saved.getId() != onBehalfOf.getId()) {
                throw new InsufficientPrivilegesException("getByEmail");
            }
        }
        return saved == null ? repo.getByEmail(email) : saved;
    }

    @Override
    public Collection<User> list(User onBehalfOf) {
        if (!onBehalfOf.isAdmin()) {
            throw new InsufficientPrivilegesException("list");
        }
        return repo.list();
    }
}
