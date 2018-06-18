package academia.controller;

import academia.business.plan.PlanRepository;
import academia.business.plan.PlanService;
import academia.model.Plan;
import academia.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/plans")
public class PlanController {

    @Autowired
    private PlanService service;

    @Autowired
    private PlanRepository repository;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Plan> all() {
        LoggedUser principal = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.all(principal.getAccountId());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Plan findById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan plan) {
        return service.update(plan);
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    public Plan create(@RequestBody Plan plan) {
        return service.create(plan);
    }
}
