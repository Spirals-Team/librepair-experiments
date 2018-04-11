package guru.bonacci.oogway.gateway.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GatewayController {

	@RequestMapping("/")
	public String ourOneAndOnlyPage() {
	    return "html/the-html.html";
	}

	@RequestMapping("/version")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
}
