package guru.bonacci.spectre.sentiment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SentimentController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

}