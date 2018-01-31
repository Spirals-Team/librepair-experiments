package ru.javawebinar.topjava.repository.jpa;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/** @author danis.tazeev@gmail.com */
@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JpaMealRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal add(Meal meal) {
        log.info("add({})", meal);
        Preconditions.checkArgument(meal.isNew(), "not new");
        em.persist(meal);
        return meal;
    }

    @Override
    @Transactional
    public Meal update(Meal meal) throws NotFoundException {
        log.info("update({})", meal);
        Preconditions.checkArgument(!meal.isNew(), "new");
        Meal m = em.find(Meal.class, meal.getId());
        log.debug("m={}", m);
        if (m == null || m.getUser().getId() != meal.getUser().getId()) {
            throw new NotFoundException("mealId=" + meal.getId() + "; userId=" + meal.getUser().getId());
        }
        if (!em.contains(meal)) {
            meal = em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public void remove(int userId, int mealId) throws NotFoundException {
        log.info("remove({}, {})", userId, mealId);
        Meal m = em.find(Meal.class, mealId);
        log.debug("m={}", m);
        if (m == null || m.getUser().getId() != userId) {
            throw new NotFoundException("mealId=" + mealId + "; userId=" + userId);
        }
        em.remove(m);
    }

    @Override
    public Meal get(int userId, int mealId) throws NotFoundException {
        log.debug("get({}, {})", userId, mealId);
        Meal m = em.find(Meal.class, mealId);
        log.debug("m={}", m);
        if (m == null || m.getUser().getId() != userId) {
            throw new NotFoundException("mealId=" + mealId + "; userId=" + userId);
        }
        return m;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Meal> list(int userId, MealListSorting sorting) {
        log.debug("list({}, {})", userId, sorting);
        return (Collection)em.createQuery("select m from Meal m where m.user.id = ?1"
                + " order by cast(m.when as date)" + sorting.getSqlPhrase() + ", cast(m.when as time)")
                .setParameter(1, userId).getResultList();
    }
}
