package guru.bonacci.oogway.gateway.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GatewayController {

	@GetMapping("/")
	public String ourOneAndOnlyPage() {
	    return "html/the-html.html";
	}

	@GetMapping("/version")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
}
