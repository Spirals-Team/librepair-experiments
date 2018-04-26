package ru.neustupov.votingforrestaurants.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.votingforrestaurants.model.Meal;
import ru.neustupov.votingforrestaurants.to.MealTo;
import ru.neustupov.votingforrestaurants.util.ControllerUtil;
import ru.neustupov.votingforrestaurants.util.MealUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/admin/meals")
public class AdminMealAjaxController extends AbstractMealController {

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id,
                    @RequestParam("menuId") int menuId) {
        return super.get(id, menuId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getAll(@RequestParam("menuId") int menuId) {
        return super.getAll(menuId);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id,
                       @RequestParam("menuId") int menuId) {
        super.delete(id, menuId);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid MealTo mealTo, BindingResult result) {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        if (result.hasErrors()) {
            return ControllerUtil.bindResultErr(result);
        }

        if (mealTo.isNew()) {
            super.create(MealUtil.createNewFromTo(mealTo), mealTo.getMenuId());
        } else {
            super.update(mealTo.getId(), mealTo, mealTo.getMenuId());
        }

        return responseEntity;
    }
}
