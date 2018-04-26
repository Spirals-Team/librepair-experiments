package ru.neustupov.votingforrestaurants.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.service.MealService;
import ru.neustupov.votingforrestaurants.to.MealTo;

import java.util.List;

import static ru.neustupov.votingforrestaurants.util.ValidationUtil.checkNew;

public class AbstractMealController {

    private static final Logger log = LoggerFactory.getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal, int menuId){
        checkNew(meal);
        log.info("create {} for menu {}", meal, menuId);
        return service.create(meal, menuId);
    }

    public void delete(int id, int menuId){
        log.info("delete meal {} for menu {}", id, menuId);
        service.delete(id, menuId);
    }

    public Meal get(int id, int menuId) {
        log.info("get meal {} for menu {}", id, menuId);
        return service.get(id, menuId);
    }

    public void update(int id, Meal meal, int menuId){
        meal.setId(id);
        log.info("update {} with id = {} for menu {}", meal, id, menuId);
        service.update(meal, menuId);
    }

    public void update(int id, MealTo mealTo, int menuId){
        mealTo.setId(id);
        log.info("update {} with id = {} for menu {}", mealTo, id, menuId);
        service.update(mealTo, menuId);
    }

    public List<Meal> getAll(int menuId){
        log.info("getAll meals for menu {}", menuId);
        return service.getAll(menuId);
    }
}
