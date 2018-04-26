package ru.neustupov.votingforrestaurants.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.votingforrestaurants.model.Meal;

import java.util.List;

@RestController
@RequestMapping(ProfileMealRestController.REST_URL)
public class ProfileMealRestController extends AbstractMealController{

    static final String REST_URL = "/rest/profile/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id, @RequestParam("menuId") int menuId) {
        return super.get(id, menuId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getAll(@RequestParam("menuId") int menuId) {
        return super.getAll(menuId);
    }
}
