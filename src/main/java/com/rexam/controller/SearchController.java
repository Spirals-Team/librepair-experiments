package com.rexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rexam.dao.TeachingUnitRepository;

@Controller
public class SearchController {
	
	@Autowired TeachingUnitRepository tuRep;
	
	@RequestMapping(value="/search")
	public ModelAndView Search(@RequestParam(required = false) String searchTerm) {
	    ModelAndView mav = new ModelAndView("search");

	    mav.addObject("searchTerm", searchTerm);
	    mav.addObject("searchResults", tuRep.findByNameIgnoreCaseContaining(searchTerm.trim()));      

	    return mav;
	}

}
