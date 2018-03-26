package guru.bonacci.oogway.heimdall.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GateController {

	@RequestMapping("/")
	public String ourOneAndOnlyPage() {
	    return "html/the-html.html";
	}

	@RequestMapping("/version")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
}
