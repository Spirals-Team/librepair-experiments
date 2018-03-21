package sysc4806;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by CraigBook on 2018-03-13.
 */
@Controller
@RequestMapping(path="/")
public class MainController {

    @GetMapping(path="/")
    public @ResponseBody
    String returnHello() {
        // This returns a JSON or XML with the users
        return "Welcome to Project Management System";
    }

}
