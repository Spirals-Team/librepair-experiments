package com.tommasopuccetti.server;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebController {
	
	@RequestMapping("/")
	public String welcome(Model model) {
		return "index";
}
}
