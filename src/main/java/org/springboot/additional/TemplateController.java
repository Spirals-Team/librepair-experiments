package org.springboot.additional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {

    @RequestMapping("/template")
    public String showView() {
        return "hello-world";
    }
}
