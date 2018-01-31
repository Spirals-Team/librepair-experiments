package ru.javawebinar.topjava.repository.jpa;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.DuplicateException;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;

/** @author danis.tazeev@gmail.com */
@Repository
final class JpaUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(JpaUserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public User add(User user) throws DuplicateException {
        log.info("add({})", user);
        Preconditions.checkArgument(user.isNew(), "not new");
        try {
            em.persist(user);
            return user;
        } catch (PersistenceException ex) {
            log.warn("add({})", user, ex);
            throw ex;
        }
    }

    @Override
    public User update(User user) throws NotFoundException, DuplicateException {
        log.info("update({})", user);
        Preconditions.checkArgument(!user.isNew(), "new");
        User u = em.find(User.class, user.getId());
        if (u == null) {
            throw new NotFoundException("id=" + user.getId());
        }
        if (!em.contains(user)) {
            user = em.merge(user);
        }
        return user;
    }

    @Override
    public void remove(int id) throws NotFoundException {
        log.info("remove({})", id);
        try {
            User u = em.getReference(User.class, id);
            em.remove(u);
        } catch (EntityNotFoundException ex) {
            log.warn("remove({})", id, ex);
            throw new NotFoundException("id=" + id);
        }
    }

    @Override
    public User get(int id) throws NotFoundException {
        log.debug("get({})", id);
        User u = em.find(User.class, id);
        log.debug("u={}", u);
        if (u == null) {
            throw new NotFoundException("id=" + id);
        }
        return u;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        log.debug("getByEmail({})", email);
        try {
            return (User)em.createQuery("select u from User u where u.email = ?1")
                    .setParameter(1, email).getSingleResult();
        } catch (NoResultException ex) {
            log.warn("getByEmail({})", email, ex);
            throw new NotFoundException("email=" + email);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> list() {
        log.debug("list()");
        return (Collection)em.createQuery("select u from User u order by u.name, u.email").getResultList();
    }

    @Override
    public boolean atMostOneAdminLeft() {
        log.debug("atMostOneAdminLeft()");
        return (long)em.createQuery("select count(u) from User u where u.admin = true").getSingleResult() <= 1;
    }
}
