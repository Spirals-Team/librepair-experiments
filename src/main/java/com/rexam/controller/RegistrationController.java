package com.rexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rexam.service.AuthentificationFacade;
import com.rexam.service.RegistrationService;

@Controller
@RequestMapping("/rexam")
public class RegistrationController {
	@Autowired
	AuthentificationFacade authentificationFacade;
	@Autowired
	RegistrationService registrationService;

	@RequestMapping("/registration")
	public String register(@RequestParam(value = "code", required = true) String codeTU, RedirectAttributes red) {

		try {
			registrationService.registration(authentificationFacade.getAuthentication().getName(), codeTU);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/rexam/showTeachingUnits";
	}

}
