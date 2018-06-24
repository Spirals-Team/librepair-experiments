package pl.edu.prz.kia.universityproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.prz.kia.universityproject.model.Faculty;
import pl.edu.prz.kia.universityproject.model.Specialization;
import pl.edu.prz.kia.universityproject.model.User;
import pl.edu.prz.kia.universityproject.service.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;


@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private UserAnswerService userAnswerService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SpecializationService specializationService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
 
	 @GetMapping(value="/admin/edit/{userId}")
    public ModelAndView adminUserEdit(@PathVariable Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getOne(userId);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/edit");
        return modelAndView;
    }

    @GetMapping(value = "/admin/facultiesList")
    public ModelAndView facultiesList() {
        ModelAndView modelAndView = new ModelAndView();
        List<Faculty> faculties = facultyService.findAll();
        List<Specialization> specializations = specializationService.findAll();
        List <User> users = userService.findAll();
        modelAndView.addObject("faculties", faculties);
        modelAndView.addObject("specs", specializations);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/facultiesList");
        return modelAndView;
    }
   
    @PostMapping(value = "/admin/edit/{userId}")
    public String editUser(@PathVariable Long userId, @ModelAttribute("updateUser") User updateUser) {
        ModelAndView modelAndView = new ModelAndView();
        userService.updateUser(updateUser);
        modelAndView.addObject("successMessage", "Dane uzytkownika zostaly zaktualizowane.");
        return "redirect:/admin/userList";
    }
	
    @GetMapping(value="/admin/home")
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Witaj " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","To jest widoczne tylko dla admina");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping(value="/admin/userList")
    public ModelAndView adminUserList(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List <User> users = userService.findAllUsersCustomQuery();
        modelAndView.addObject("users", users);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/userList");
        return modelAndView;
    }

    @GetMapping(value="/admin/adminList")
    public ModelAndView adminList(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List <User> users = userService.findAll();
        modelAndView.addObject("users", users);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/adminList");
        return modelAndView;
    }

    @GetMapping(value="/admin/delete")
    public String adminUserDelete(@RequestParam(name="userId")Long userId) {
        userService.deleteUser(userId);
        return "redirect:userList";
    }
    @GetMapping(value="/admin/facultiesSpecializationsList")
    public ModelAndView adminFacultiesSpecializationsList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Faculty> faculties = facultyService.findAll();
        List <Specialization> specializations = specializationService.findAll();
        modelAndView.addObject("faculties", faculties);
        modelAndView.addObject("specializations", specializations);
        modelAndView.setViewName("admin/facultiesSpecializationsList");
        return modelAndView;
    }
    
    @GetMapping(value="/admin/facultiesAdd")
    public ModelAndView adminFacultiesAdd(){
        ModelAndView modelAndView = new ModelAndView();

        Faculty faculty = new Faculty();
        modelAndView.addObject("faculty", faculty);
        modelAndView.setViewName("admin/facultiesAdd");
        return modelAndView;
    }

    
    @PostMapping(value = "/admin/facultiesAdd")
    public ModelAndView createNewFaculty(@Valid Faculty faculty, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Faculty facultyExists = facultyService.findFacultyByName(faculty.getName());
        if (facultyExists != null) {
            bindingResult
                    .rejectValue("name", "error.faculty",
                            "Juz istnieje taki wydzial.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/facultiesAdd");
        } else {

            facultyService.saveFaculty(faculty);
            modelAndView.addObject("successMessage", "Dodano wydzial.");
            modelAndView.addObject("faculty", new Faculty());
            modelAndView.setViewName("redirect:facultiesSpecializationsList");

        }
        return modelAndView;
    }

    @GetMapping(value="/admin/facultiesSpecializationsAdd")
    public ModelAndView adminFacultiesSpecializationsAdd(){
        ModelAndView modelAndView = new ModelAndView();
        Specialization specialization = new Specialization();
        List<Faculty> faculties =  facultyService.findAll();
        modelAndView.addObject("specialization", specialization);
        modelAndView.addObject("faculties", faculties);
        modelAndView.setViewName("admin/facultiesSpecializationsAdd");
        return modelAndView;
    }

    @PostMapping(value = "/admin/facultiesSpecializationsAdd")
    public ModelAndView createNewSpecialisation(@Valid Specialization specialization, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<Faculty> faculties = facultyService.findAll();
        Specialization specializationExists = specializationService.findSpecializationByName(specialization.getName());
        if (specializationExists != null) {
            bindingResult
                    .rejectValue("name", "error.specialization",
                            "Juz istnieje taki kierunek.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/facultiesSpecializationsAdd");
            modelAndView.addObject("faculties", faculties);
        } else {

                specializationService.saveSpecialization(specialization);
                modelAndView.addObject("successMessage", "Dodano kierunek.");
                modelAndView.addObject("faculty", new Specialization());
                modelAndView.addObject("faculties", faculties);
                modelAndView.setViewName("redirect:facultiesSpecializationsList");

        }
        return modelAndView;
    }
}
