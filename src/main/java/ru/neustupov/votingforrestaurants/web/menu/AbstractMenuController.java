package ru.neustupov.votingforrestaurants.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.votingforrestaurants.model.Menu;
import ru.neustupov.votingforrestaurants.service.MenuService;

import java.util.List;

import static ru.neustupov.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.votingforrestaurants.util.ValidationUtil.checkNew;

public abstract class AbstractMenuController {

    private static final Logger log = LoggerFactory.getLogger(AbstractMenuController.class);

    @Autowired
    private MenuService service;

    public Menu get(int id, int restId) {
        log.info("get menu {} for restaurant {}", id, restId);
        return service.get(id, restId);
    }

    public void delete(int id, int restId) {
        log.info("delete menu {} for restaurant {}", id, restId);
        service.delete(id, restId);
    }

    public List<Menu> getAll(int restId) {
        log.info("getAll menus for restaurant {}", restId);
        return service.getAll(restId);
    }

    public Menu getTodaysMenuWithMeals(int restId){
        log.info("getTodaysMenuWithMeals for restaurant {}", restId);
        return service.getTodaysMenuWithMeals(restId);
    }

    public Menu create(Menu menu, int restId) {
        checkNew(menu);
        log.info("create {} for restaurant {}", menu, restId);
        return service.create(menu, restId);
    }

    public void update(int id, Menu menu, int restId) {
        menu.setId(id);
        log.info("update {} with id = {} for restaurant {}", menu, id, restId);
        service.update(menu, restId);
    }
}
