package ru.javawebinar.topjava.service;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.NotFoundException;

import java.util.Collection;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class MealServiceImpl implements MealService {
    private static final Logger log = LoggerFactory.getLogger(MealServiceImpl.class);
    private final MealRepository repo;

    @Autowired
    public MealServiceImpl(MealRepository repo) {
        Objects.requireNonNull(repo, "repo");
        this.repo = repo;
    }

    @Override
    @Transactional
    public Meal add(Meal meal) {
        Preconditions.checkArgument(meal.isNew(), "not new");
        return repo.add(meal);
    }

    @Override
    @Transactional
    public void update(Meal meal) throws NotFoundException {
        log.info("update({})", meal);
        Preconditions.checkArgument(!meal.isNew(), "new");
        repo.update(meal);
    }

    @Override
    @Transactional
    public void remove(int userId, int mealId) throws NotFoundException {
        log.info("remove({}, {})", userId, mealId);
        repo.remove(userId, mealId);
    }

    @Override
    public Meal get(int userId, int mealId) throws NotFoundException {
        return repo.get(userId, mealId);
    }

    @Override
    public Collection<Meal> list(int userId, MealListSorting sorting) {
        Objects.requireNonNull(sorting, "sorting");
        return repo.list(userId, sorting);
    }
}
