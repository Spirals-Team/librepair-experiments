package com.rexam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rexam.dao.CurrentYearRepository;
import com.rexam.dao.RegistrationRepository;
import com.rexam.dao.ResultRepository;
import com.rexam.dao.StudentRepository;
import com.rexam.dao.StudentYearRepository;
import com.rexam.dao.TeachingUnitRepository;
import com.rexam.dao.UserRepository;
import com.rexam.model.Component;
import com.rexam.model.CurrentYear;
import com.rexam.model.Exam;
import com.rexam.model.Registration;
import com.rexam.model.Student;
import com.rexam.model.StudentYear;
import com.rexam.model.TeachingUnit;
import com.rexam.model.User;
import com.rexam.service.AuthentificationFacade;
import com.rexam.service.DetailResultService;

@Controller
@RequestMapping("/rexam")
public class StudentController {

	@Autowired
	TeachingUnitRepository tuRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	StudentYearRepository studYearRepository;
	@Autowired
	ResultRepository resultRepository;
	@Autowired
	RegistrationRepository regRepository;
	@Autowired
	CurrentYearRepository yearRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthentificationFacade authentificationFacade;

	@RequestMapping("/showTeachingUnits")
	public ModelAndView showDisciplines() {
		List<String> disciplines = tuRepository.findDisciplines();
		List<TeachingUnit> tuList = tuRepository.findAllByOrderByDisciplineAsc();

		ModelAndView mav = new ModelAndView("teachingUnits");
		mav.addObject("disciplines", disciplines);
		StudentYear student = studYearRepository.findById_YearAndStudent(currentYear(), student());
		List<Registration> regs = regRepository.findByStudentYear(student);
		for (int i = 0; i < regs.size(); i++) {
			for (int j = 0; j < tuList.size(); j++) {
				if (regs.get(i).getTeachingUnit().getCode().equals(tuList.get(j).getCode())) {
					tuList.remove(j);
				}
			}
		}
		mav.addObject("tuList", tuList);
		return mav;
	}

	@RequestMapping("/showExams")
	public ModelAndView showExams(@RequestParam(value = "code", required = false) String codeTU,
			HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		String role = "student";

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().equals("admin")) {
				role = "admin";
			}
		}
		TeachingUnit tu = tuRepository.findOne(codeTU);

		ModelAndView mav = new ModelAndView("exams");
		mav.addObject("teachingUnit", tu);
		mav.addObject("role", role);

		return mav;
	}

	@RequestMapping("/addTeachingUnits")
	public String addTeachingUnits() {
		TeachingUnit u = new TeachingUnit();
		u.setCode("UE1");
		u.setName("Programmation Haskell");
		u.setDiscipline("Informatique");
		u.setCreditValue(3);
		Component c = new Component();
		c.setId(1);
		c.setWeight(2);
		Exam e = new Exam();
		e.setCode("Epr1Haskell");
		e.setTypeExam("partiel");
		c.setExam(e);
		List<Component> comps = new ArrayList<Component>();
		comps.add(c);
		u.setComponents(comps);
		tuRepository.save(u);
		TeachingUnit u2 = new TeachingUnit();
		u2.setCode("UE2");
		u2.setName("philo");
		u2.setDiscipline("Lettres");
		u2.setCreditValue(2);
		tuRepository.save(u2);
		TeachingUnit u3 = new TeachingUnit();
		u3.setCode("UE3");
		u3.setName("Dictée");
		u3.setDiscipline("Lettres");
		u3.setCreditValue(3);
		tuRepository.save(u3);
		return "index";
	}

	@RequestMapping("/regs")
	public ModelAndView listRegistrations() {

		StudentYear student = studYearRepository.findById_YearAndStudent(currentYear(), student());
		List<Registration> regs = regRepository.findByStudentYear(student);

		if (regs == null) {
			regs = new ArrayList<Registration>();
		}

		ModelAndView mav = new ModelAndView("regslist");
		mav.addObject("regs", regs);

		return mav;
	}

	@RequestMapping("/results")
	public ModelAndView listResults() {
		StudentYear student = studYearRepository.findById_YearAndStudent(currentYear(), student());
		List<Registration> regs = regRepository.findByStudentYear(student);

		if (regs == null) {
			regs = new ArrayList<Registration>();
		}

		ModelAndView mav = new ModelAndView("reslist");
		mav.addObject("results", regs);

		return mav;
	}

	@RequestMapping("/results/{codeTU}")
	public ModelAndView detailResults(@PathVariable(value = "codeTU") String codeTU) {
		TeachingUnit tu = tuRepository.findOne(codeTU);
		StudentYear student = studYearRepository.findById_YearAndStudent(currentYear(), student());

		List<DetailResultService> detailRes = new ArrayList<DetailResultService>();
		DetailResultService tmpLine;

		for (Component compo : tu.getComponents()) {
			tmpLine = new DetailResultService();

			tmpLine.setTypeExam(compo.getExam().getTypeExam());
			tmpLine.setScore(resultRepository.findByExamAndStudentYear(compo.getExam(), student).getScore());
			tmpLine.setWeight(compo.getWeight());
			tmpLine.setDateObt(resultRepository.findByExamAndStudentYear(compo.getExam(), student).getDateObtened());

			detailRes.add(tmpLine);
		}

		ModelAndView mav = new ModelAndView("detailRes");
		mav.addObject("tuname", tu.getName());

		mav.addObject("detailRes", detailRes);

		mav.addObject("studyear", student);

		return mav;
	}

	@ModelAttribute("teachingUnits")
	Iterable<TeachingUnit> teachingUnits() {
		return tuRepository.findAll();
	}

	@ModelAttribute("currentYear")
	Integer currentYear() {
		return ((Collection<CurrentYear>) yearRepository.findAll()).stream().findFirst().get().getYear();
	}
	@ModelAttribute("student")
	Student student() {
		return studentRepository.findByEmail(authentificationFacade.getAuthentication().getName());
	}
	@ModelAttribute("user")
	User user() {
		return userRepository.findByEmail(authentificationFacade.getAuthentication().getName());
	}

}