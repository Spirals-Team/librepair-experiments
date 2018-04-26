package ru.neustupov.votingforrestaurants.web.menu;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.neustupov.votingforrestaurants.model.Menu;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminMenuRestController.REST_URL)
public class AdminMenuRestController extends AbstractMenuController {

    static final String REST_URL = "/rest/admin/menus";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @RequestParam("restId") int restId) {
        return super.get(id, restId);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @RequestParam("restId") int restId) {
        super.delete(id, restId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(@RequestParam("restId") int restId) {
        return super.getAll(restId);
    }

    @Override
    @GetMapping(value = "/todays", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getTodaysMenuWithMeals(@RequestParam("restId") int restId) {
        return super.getTodaysMenuWithMeals(restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu, @RequestParam("restId") int restId) {
        Menu created = super.create(menu, restId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") int id, @RequestBody Menu menu, @RequestParam("restId") int restId) {
        super.update(id, menu, restId);
    }
}
