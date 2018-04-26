package ru.neustupov.votingforrestaurants.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.repository.MealRepository;

import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudMenuRepository crudMenuRepository;

    @Transactional
    @Override
    public Meal save(Meal meal, int menuId) {
        if (!meal.isNew() && get(meal.getId(), menuId) == null) {
            return null;
        }
        meal.setMenu(crudMenuRepository.getOne(menuId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudMealRepository.delete(id, menuId) != 0;
    }

    @Override
    public Meal get(int id, int menuId) {
        return crudMealRepository.findById(id).filter(meal -> meal.getMenu().getId() == menuId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int menuId) {
        return crudMealRepository.getAll(menuId);
    }

    @Override
    public Meal getWithMenu(int id) {
        return crudMealRepository.getWithMenu(id);
    }
}
