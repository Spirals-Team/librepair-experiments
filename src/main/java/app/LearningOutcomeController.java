package app;

import app.LearningOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import app.LearningOutcomeRepository;

import java.util.List;
import java.util.Map;

@Controller
public class LearningOutcomeController {

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepo;

    @Autowired
    private ProgramRepository progRepo;


    @GetMapping("/learningOutcome")
    public String loForm(Model model){
        LearningOutcome learningOutcome = new LearningOutcome("test", "test");
        model.addAttribute("learningOutcome", learningOutcome);
        return "learningOutcome";
    }

    @RequestMapping("/listLearningOutcomes")
    public String listLearningOutcomes(Model model){
        model.addAttribute("learningOutcomes", learningOutcomeRepo.findAll());
        return "listLearningOutcomes";
    }

    @RequestMapping("/listLearningOutcomesByCategory")
    public String listLearningOutcomesByCategory(@ModelAttribute("category") Category category, Model model){
        model.addAttribute("learningOutcomes", learningOutcomeRepo.findByCategory(category));
        return "listLearningOutcomes";
    }

    @RequestMapping("/listLearningOutcomesByCourse")
    public String listLearningOutcomesByCourse(@ModelAttribute("course") Course course, Model model){
        model.addAttribute("learningOutcomes", learningOutcomeRepo.findByCourse(course));
        return "listLearningOutcomes";
    }

    @GetMapping("/addLearningOutcome")
    public String learningOutcomeForm(Model model){
        LearningOutcome learningOutcome = new LearningOutcome();
        model.addAttribute("learningOutcome", learningOutcome);
        return "learningOutcomeForm";
    }

    @PostMapping("/addLearningOutcome")
    public String learningOutcomeSubmit(@ModelAttribute("learningOutcome") LearningOutcome learningOutcome) {
        learningOutcomeRepo.save(learningOutcome);
        return "loResult";
    }
}
