package com.rexam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rexam.dao.ComponentRepository;
import com.rexam.dao.CurrentYearRepository;
import com.rexam.dao.ExamRepository;
import com.rexam.dao.RegistrationRepository;
import com.rexam.dao.ResultRepository;
import com.rexam.dao.StudentRepository;
import com.rexam.dao.TeachingUnitRepository;
import com.rexam.model.Component;
import com.rexam.model.CurrentYear;
import com.rexam.model.Exam;
import com.rexam.model.IdRegistration;
import com.rexam.model.IdResult;
import com.rexam.model.IdStudentYear;
import com.rexam.model.Registration;
import com.rexam.model.Result;
import com.rexam.model.Student;
import com.rexam.model.StudentYear;
import com.rexam.model.TeachingUnit;

@Controller
@RequestMapping("/admin")
public class ResultController {

    @Autowired
    ResultRepository rRepository;

    @Autowired
    RegistrationRepository regRepository;

    @Autowired
    StudentRepository sRepository;

    @Autowired
    TeachingUnitRepository tuRepository;

    @Autowired
    ExamRepository eRepository;

    @Autowired
    ComponentRepository cRepository;
    
    @Autowired
    CurrentYearRepository yearRepository;
    
    @RequestMapping("/showExamResults")
    public ModelAndView showExamResults(@ModelAttribute(value = "results") Results examResults) {

        ModelAndView mav = new ModelAndView("examResults");
        return mav;
    }

    @RequestMapping("/editResults")
    public ModelAndView showExams(@ModelAttribute(value = "results") Results re,
            BindingResult result) {

        if (!result.hasErrors()) {
            rRepository.save(re.getExamResults());
        }

        for (Component c : cRepository.findByExam(re.getExamResults().get(0).getExam())) {
            for (TeachingUnit tu : tuRepository.findByComponent(c.getId())) {
                for (Registration reg : regRepository.findByIdCodeTeachingUnit(tu.getCode())) {
                    rRepository.computeAvg(tu.getCode(), reg.getStudentYear().getId().getId(),
                            reg.getStudentYear().getId().getYear());
                }
            }
        }

        return new ModelAndView("index");
    }

    @RequestMapping("/initData")
    public ModelAndView addTeachingUnits(@ModelAttribute(value = "results") Results examResults) {
        TeachingUnit teachingUnit = tuRepository.findOne("ENSPHCU89");
        Student student = sRepository.findOne("srowlands0@vimeo.com");

        IdStudentYear idStudentYear = new IdStudentYear();
        idStudentYear.setYear(2019);
        idStudentYear.setId(1);

        StudentYear studentYear = new StudentYear();
        studentYear.setStudent(student);
        studentYear.setId(idStudentYear);
        List<Registration> list1 = new ArrayList<Registration>();

        IdRegistration idRegistration = new IdRegistration();
        idRegistration.setIdStudentYear(studentYear.getId());
        idRegistration.setCodeTeachingUnit(teachingUnit.getCode());

        Registration registration = new Registration();
        registration.setStudentYear(studentYear);
        registration.setStatus("calculable");
        registration.setId(idRegistration);
        registration.setTeachingUnit(teachingUnit);
        list1.add(registration);
        studentYear.setRegistration(list1);
        regRepository.save(registration);

        IdResult idResult = new IdResult();
        idResult.setIdStudentYear(studentYear.getId());
        idResult.setCodeExam(teachingUnit.getComponents().get(0).getExam().getCode());

        Result r = new Result();

        r.setId(idResult);
        r.setExam(teachingUnit.getComponents().get(0).getExam());
        r.setStudentYear(studentYear);
        r.setScore(0.0);
        rRepository.save(r);

        idResult = new IdResult();
        idResult.setIdStudentYear(studentYear.getId());
        idResult.setCodeExam(teachingUnit.getComponents().get(1).getExam().getCode());
        r = new Result();

        r.setId(idResult);
        r.setExam(teachingUnit.getComponents().get(1).getExam());
        r.setStudentYear(studentYear);
        r.setScore(0.0);

        rRepository.save(r);

        idResult = new IdResult();
        idResult.setIdStudentYear(studentYear.getId());
        idResult.setCodeExam(teachingUnit.getComponents().get(2).getExam().getCode());
        r = new Result();

        r.setId(idResult);
        r.setExam(teachingUnit.getComponents().get(2).getExam());
        r.setStudentYear(studentYear);
        r.setScore(0.0);

        rRepository.save(r);

        ModelAndView mav = new ModelAndView("redirect:admin/showExamResults");
        return mav;
    }
    
    @RequestMapping("/initData2")
    public ModelAndView addTeachingUnits2(@ModelAttribute(value = "results") Results examResults) {
        TeachingUnit teachingUnit = tuRepository.findOne("ENSPHCU97");
        Student student = sRepository.findOne("srowlands0@vimeo.com");

        IdStudentYear idStudentYear = new IdStudentYear();
        idStudentYear.setYear(2019);
        idStudentYear.setId(1);

        StudentYear studentYear = new StudentYear();
        studentYear.setStudent(student);
        studentYear.setId(idStudentYear);
        List<Registration> list1 = new ArrayList<Registration>();

        IdRegistration idRegistration = new IdRegistration();
        idRegistration.setIdStudentYear(studentYear.getId());
        idRegistration.setCodeTeachingUnit(teachingUnit.getCode());

        Registration registration = new Registration();
        registration.setStudentYear(studentYear);
        registration.setStatus("calculable");
        registration.setId(idRegistration);
        registration.setTeachingUnit(teachingUnit);
        list1.add(registration);
        studentYear.setRegistration(list1);
        regRepository.save(registration);

        IdResult idResult = new IdResult();
        idResult.setIdStudentYear(studentYear.getId());
        idResult.setCodeExam(teachingUnit.getComponents().get(0).getExam().getCode());

        Result r = new Result();

        r.setId(idResult);
        r.setExam(teachingUnit.getComponents().get(0).getExam());
        r.setStudentYear(studentYear);
        r.setScore(0.0);
        rRepository.save(r);

        idResult = new IdResult();
        idResult.setIdStudentYear(studentYear.getId());
        idResult.setCodeExam(teachingUnit.getComponents().get(1).getExam().getCode());
        r = new Result();

        r.setId(idResult);
        r.setExam(teachingUnit.getComponents().get(1).getExam());
        r.setStudentYear(studentYear);
        r.setScore(0.0);

        rRepository.save(r);

        idResult = new IdResult();
        idResult.setIdStudentYear(studentYear.getId());
        idResult.setCodeExam(teachingUnit.getComponents().get(2).getExam().getCode());
        r = new Result();

        r.setId(idResult);
        r.setExam(teachingUnit.getComponents().get(2).getExam());
        r.setStudentYear(studentYear);
        r.setScore(0.0);

        rRepository.save(r);

        ModelAndView mav = new ModelAndView("redirect:/admin/showExamResults");
        return mav;
    }

    @ModelAttribute("results")
    Results examResutls(@RequestParam(value = "exam", required = false) Exam exam) {
        Results re = new Results();
        re.setExamResults((List<Result>) rRepository
                .findByExam(tuRepository.findOne("ENSPHCU89").getComponents().get(0).getExam()));
        return re;
    }
	@ModelAttribute("currentYear")
	Integer currentYear() {
		return ((Collection<CurrentYear>) yearRepository.findAll())
					.stream().findFirst().get().getYear();
	}

}