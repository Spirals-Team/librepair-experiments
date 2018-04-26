package ru.neustupov.votingforrestaurants.web.menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.votingforrestaurants.model.Menu;

import java.util.List;

@RestController
@RequestMapping(ProfileMenuRestController.REST_URL)
public class ProfileMenuRestController extends AbstractMenuController{

    static final String REST_URL = "/rest/profile/menus";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @RequestParam("restId") int restId) {
        return super.get(id, restId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(int restId) {
        return super.getAll(restId);
    }

    @Override
    @GetMapping(value = "/todays", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getTodaysMenuWithMeals(@RequestParam("restId") int restId) {
        return super.getTodaysMenuWithMeals(restId);
    }
}
