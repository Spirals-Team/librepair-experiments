package ru.job4j.carsale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.models.Advert;
import ru.job4j.models.User;
import ru.job4j.storage.CarStor;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Controller
public class AdminController {
    private final CarStor carStor = CarStor.INSTANCE;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getAllUsersPage() {
        return "ViewAllUsers";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public String setChangeRole(@RequestParam("id") String id) {
        User user = carStor.getuStor().findById(Integer.parseInt(id));
        if (user.getRole().equals("ROLE_USER")) {
            user.setAdmin();
        } else {
            user.setUser();
        }
        carStor.getuStor().add(user);
        return "ViewAllUsers";
    }

    @RequestMapping(value = "/admin/usersdel", method = RequestMethod.POST)
    public String delUser(@RequestParam("id") String id) {
        User user = carStor.getuStor().findById(Integer.parseInt(id));
        List<Advert> list = carStor.getaStor().findByUser(user);
        for (int i = 0; i < list.size(); i++) {
            carStor.getaStor().del(list.get(i));
        }
        carStor.getuStor().del(Integer.parseInt(id));
        return "ViewAllUsers";
    }

    @RequestMapping(value = "/admin/adverts", method = RequestMethod.GET)
    public String getAllAdvertsPage() {
        return "ViewAllAdverts";
    }

    @RequestMapping(value = "/admin/adverts", method = RequestMethod.POST)
    public String delAdvert(@RequestParam("id") String id) {
        carStor.getaStor().del(Integer.parseInt(id));
        return "ViewAllAdverts";
    }
}
