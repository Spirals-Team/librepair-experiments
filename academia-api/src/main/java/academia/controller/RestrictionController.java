package academia.controller;

import academia.business.restriction.RestrictionRepository;
import academia.business.restriction.RestrictionService;
import academia.model.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/restrictions")
public class RestrictionController {

    @Autowired
    private RestrictionRepository restrictionRepository;

    @Autowired
    private RestrictionService restrictionService;

    // TODO validar roles dos usuários nesses requests, apenas role admin pode buscar todos etc...

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Restriction> all() {
        return restrictionRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Restriction findById(@PathVariable Long id) {
        return restrictionRepository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public Restriction update(@PathVariable Long id, @RequestBody Restriction restriction) {
        return restrictionService.update(restriction);
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    public Restriction create(@RequestBody Restriction restriction) {
        return restrictionService.create(restriction);
    }


}
