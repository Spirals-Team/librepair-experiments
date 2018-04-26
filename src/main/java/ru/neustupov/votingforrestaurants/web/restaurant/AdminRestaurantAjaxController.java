package ru.neustupov.votingforrestaurants.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.votingforrestaurants.model.Restaurant;
import ru.neustupov.votingforrestaurants.to.RestaurantWithVotes;
import ru.neustupov.votingforrestaurants.util.ControllerUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/admin/restaurants")
public class AdminRestaurantAjaxController extends AbstractRestaurantController{

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVotes> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid Restaurant restaurant, BindingResult result) {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        if (result.hasErrors()) {
            return ControllerUtil.bindResultErr(result);
        }

        if (restaurant.isNew()) {
            super.create(restaurant);
        }else {
            super.update(restaurant, restaurant.getId());
        }

        return responseEntity;
    }
}
