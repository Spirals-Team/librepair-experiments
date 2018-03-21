package sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/test")
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping(path="/add")
    public @ResponseBody String addNewStudent (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Student s = new Student();
        s.setName(name);
        studentRepo.save(s);
        return "Saved: " + name;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Student> getAllStudents() {
        // This returns a JSON or XML with the users
        return studentRepo.findAll();
    }

    @GetMapping(path="/test")
    public @ResponseBody String returnHello() {
        // This returns a JSON or XML with the users
        return "Hello";
    }
}