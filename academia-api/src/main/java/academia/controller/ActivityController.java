package academia.controller;

import academia.business.activity.ActivityRepository;
import academia.business.activity.ActivityService;
import academia.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Activity> all() {
        return activityRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Activity findById(@PathVariable Long id) {
        return activityRepository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public Activity update(@PathVariable Long id, @RequestBody Activity activity) {
        return activityService.update(activity);
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    public Activity create(@RequestBody Activity activity) {
        return activityService.create(activity);
    }
}
