package pl.sda.jira.project.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sda.jira.project.model.ProjectDto;
import pl.sda.jira.project.model.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {


    private final ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public long create(@ModelAttribute ProjectDto projectDto) {
        return service.add(projectDto);}

    @RequestMapping(path = "/{identifier}", method = RequestMethod.DELETE)
    public void remove(@PathVariable long identifier) {
        service.delete(identifier);
    }

    @RequestMapping(path = "/{identifier}", method = RequestMethod.GET)
    public ProjectDto get(@PathVariable long identifier) {
        return service.get(identifier);
    }

    @RequestMapping(path = "/{identifier}", method = RequestMethod.POST)
    public void update(@PathVariable long identifier, @ModelAttribute ProjectDto projectDto) {
         service.update(identifier,projectDto);
    }


}
