package ru.neustupov.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.repository.MealRepository;
import ru.neustupov.votingforrestaurants.to.MealTo;
import ru.neustupov.votingforrestaurants.util.MealUtil;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService{

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository){
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int menuId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal, menuId);
    }

    @Override
    public void delete(int id, int menuId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, menuId), id);
    }

    @Override
    public Meal get(int id, int menuId) throws NotFoundException {
        Assert.notNull(menuId, "menuId must not be null");
        return checkNotFoundWithId(repository.get(id, menuId), id);
    }

    @Override
    public void update(Meal meal, int menuId) {
        Assert.notNull(meal, "meal must not be null");
        Assert.notNull(menuId, "menuId must not be null");
        checkNotFoundWithId(repository.save(meal, menuId), meal.getId());
    }

    @Transactional
    @Override
    public void update(MealTo mealTo, int menuId) {
        Assert.notNull(mealTo, "meal must not be null");
        Assert.notNull(menuId, "menuId must not be null");
        Meal meal = get(mealTo.getId(), menuId);
        checkNotFoundWithId(repository.save(MealUtil.updateFromTo(meal, mealTo), menuId), mealTo.getId());
    }

    @Override
    public List<Meal> getAll(int menuId) {
        Assert.notNull(menuId, "menuId must not be null");
        return repository.getAll(menuId);
    }

    @Override
    public Meal getWithMenu(int id) {
        return checkNotFoundWithId(repository.getWithMenu(id), id);
    }
}
