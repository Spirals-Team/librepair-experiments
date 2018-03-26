package guru.bonacci.oogway.config.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfigController {

    @RequestMapping("/")
    public @ResponseBody String version() {
        return "Hello World";
    }
}