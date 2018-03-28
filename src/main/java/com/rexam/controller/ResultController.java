package com.rexam.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rexam.dao.AdminRepository;
import com.rexam.dao.CurrentYearRepository;
import com.rexam.dao.ExamRepository;
import com.rexam.dao.RegistrationRepository;
import com.rexam.dao.ResultRepository;
import com.rexam.model.CurrentYear;
import com.rexam.model.Result;
import com.rexam.model.TeachingUnit;
import com.rexam.model.User;
import com.rexam.service.AuthentificationFacade;
import com.rexam.service.RegistrationService;
import com.rexam.service.ResultEditionService;

@Controller
@RequestMapping("/admin")
public class ResultController {

    @Autowired
    ResultRepository rRepository;

    @Autowired
    ResultEditionService resService;

    @Autowired
    RegistrationService regService;

    @Autowired
    CurrentYearRepository yearRepository;

    @Autowired
    RegistrationRepository regRepository;

    @Autowired
    ExamRepository exRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AuthentificationFacade authentificationFacade;

    @RequestMapping("/showTU")
    public ModelAndView showTU(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {

        String role = "student";

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals("admin")) {
                role = "admin";
            }
        }

        List<String> disciplines = regRepository.findDisciplines();
        List<TeachingUnit> tuList = regRepository.findTeachingUnits();

        ModelAndView mav = new ModelAndView("teachingUnits");
        mav.addObject("disciplines", disciplines);
        mav.addObject("tuList", tuList);
        mav.addObject("role", role);
        return mav;
    }

    @RequestMapping("/showExamResults")
    public ModelAndView showExamResults(@ModelAttribute(value = "results") Results re,
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {

        String role = "student";

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals("admin")) {
                role = "admin";
            }
        }
        LocalDate current_date = LocalDate.now();

        for (Result r : re.getExamResults()) {
            r.setDateObtened(current_date.toString());
        }

        ModelAndView mav = new ModelAndView("examResults");
        mav.addObject("results", re);
        mav.addObject("role", role);

        return mav;
    }

    @RequestMapping("/editResults")
    public ModelAndView processEdition(
            @RequestParam(value = "codeExam", required = false) String exam,
            @ModelAttribute(value = "results") @Valid Results re, BindingResult result) {

        if (!result.hasErrors()) {
            for (Result res : re.getExamResults())
                System.out.println(res.getStudentYear().getId().getYear() + " ");

            rRepository.save(re.getExamResults());
            resService.updateStatus(re.getExamResults().get(0).getExam());
            resService.computeAvg(re.getExamResults().get(0).getExam());
            
            
            
            return new ModelAndView("redirect:/admin/showTU");
        }

        else {
            ModelAndView mav = new ModelAndView("redirect:/admin/showExamResults?codeExam=" + exam);
            mav.addObject("ErrorMessage", "Les notes doivent être entre 0 et 20");
            return mav;
        }
    }

    @RequestMapping("/initData")
    public ModelAndView addTeachingUnits(@ModelAttribute(value = "results") Results examResults) {
        try {
            regService.registration("srowlands0@vimeo.com", "ENSPHCU89");
            regService.registration("srowlands0@vimeo.com", "ENSPHCU97");
            regService.registration("rsemper2o@comsenz.com", "ENSPHCU89");
            regService.registration("rsemper2o@comsenz.com", "ENSPHCU97");
            regService.registration("jbelhome2p@microsoft.com", "ENASPCU1");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ModelAndView mav = new ModelAndView("redirect:/admin/showTU");
        return mav;
    }

    @ModelAttribute("results")
    Results examResutls(@ModelAttribute("currentYear") Integer currentYear,
            @RequestParam(value = "codeExam", required = false) String exam, ModelMap model) {
        if (exam == null)
            return (Results) model.get("results");

        Results re = new Results();
        re.setExamResults(rRepository.findByExam(exRepository.findOne(exam)));
        return re;
    }

    @ModelAttribute("currentYear")
    Integer currentYear() {
        return ((Collection<CurrentYear>) yearRepository.findAll()).stream().findFirst().get()
                .getYear();
    }

    @ModelAttribute("user")
    User user() {
        return adminRepository.findByEmail(authentificationFacade.getAuthentication().getName());
    }

}