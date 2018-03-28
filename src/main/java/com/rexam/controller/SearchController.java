package com.rexam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rexam.dao.TeachingUnitRepository;
import com.rexam.model.TeachingUnit;

@Controller
@RequestMapping("/rexam")
public class SearchController {
	
	@Autowired TeachingUnitRepository tuRep;
	
	@RequestMapping(value="/search")
	public ModelAndView Search(@RequestParam(required = false) String searchTerm) {
	    ModelAndView mav = new ModelAndView("search");
	    String searchLower = searchTerm.trim().toLowerCase();
	    List<TeachingUnit> results = null;
	    
	    if (!searchLower.equals(""))
	    	results = tuRep.findDistinctByDisciplineOrName(searchLower); 
	    
	    mav.addObject("searchTerm", searchTerm);
	    mav.addObject("searchResults", results);      
	    return mav;
	}

}
