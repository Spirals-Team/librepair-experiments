package guru.bonacci.oogway.heimdall.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GateController {

	@RequestMapping("/")
	public String ourOneAndOnlyPage() {
	    return "html/the-html.html";
	}
}
