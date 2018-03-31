package com.myanmareffective.demo.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	private static final String WELCOME_PATH = "welcome";

	@GetMapping("/")
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView(WELCOME_PATH);
		getTicketRevenue(mav);
		return mav;
	}

	private void getTicketRevenue(ModelAndView mav) {
		TicketRevenue ticketRevenue = new TicketRevenue();
		mav.addObject("ticketRevenue", ticketRevenue.estimateTotalRevenue(1));
	}

}