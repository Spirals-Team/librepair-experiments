package sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/project")
public class ProjectController {

    @Autowired
    private ProjectRepo projectRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/add")
    public @ResponseBody String addNewProject (@RequestParam String title, @RequestParam String description, @RequestParam String programs, @RequestParam int maxStudents) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Project p = new Project();
        //List<String> programList = Arrays.asList(programs.split("\\s*,\\s*"));
        p.setTitle(title);
        p.setDescription(description);
        p.setStudentLimit(maxStudents);
        p.setPrograms(programs);
        projectRepo.save(p);
        return "Saved project: " + title;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/deleteById")
    public @ResponseBody String deleteProjectById (@RequestParam long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        projectRepo.deleteById(id);
        return "Delete: " + id;
    }

    @GetMapping(path="/deleteAll")
    public @ResponseBody String deleteAllProjects () {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        projectRepo.deleteAll();
        return "Delete All";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Project> getAllStudents() {
        // This returns a JSON or XML with the users
        return projectRepo.findAll();
    }

    @GetMapping(path="/test")
    public @ResponseBody String returnHello() {
        // This returns a JSON or XML with the users
        return "Hello";
    }
}