package com.rexam.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rexam.dao.CurrentYearRepository;
import com.rexam.dao.TeachingUnitRepository;
import com.rexam.dao.UserRepository;
import com.rexam.model.CurrentYear;
import com.rexam.model.User;
import com.rexam.service.AuthentificationFacade;

@Controller
public class SearchController {
	@Autowired
	CurrentYearRepository yearRepository;
	@Autowired
	TeachingUnitRepository tuRep;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthentificationFacade authentificationFacade;

	@RequestMapping(value = "/search")
	public ModelAndView Search(@RequestParam(required = false) String searchTerm) {
		ModelAndView mav = new ModelAndView("search");

		mav.addObject("searchTerm", searchTerm);
		mav.addObject("searchResults", tuRep.findByNameIgnoreCaseContaining(searchTerm.trim()));

		return mav;
	}

	@ModelAttribute("currentYear")
	Integer currentYear() {
		return ((Collection<CurrentYear>) yearRepository.findAll()).stream().findFirst().get().getYear();
	}
	@ModelAttribute("user")
	User user() {
		return userRepository.findByEmail(authentificationFacade.getAuthentication().getName());
	}
}
