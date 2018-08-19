package ua.epam.spring.hometask.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Vladimir_Vysokomorny on 20-Dec-17.
 */
@Controller
@SessionAttributes("userAccount")
public class LoginController {

    //@RequestMapping(value = "/login", method = RequestMethod.GET)
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView mav = new ModelAndView("login");
        if(error!=null){
            mav.addObject("error", "error");
        }
        if(logout!=null){
            mav.addObject("logout", "logout");
        }

        return mav;
    }
    @RequestMapping(value = "/logoutPage", method = RequestMethod.GET)
    public String logoutPage() {

        return "logoutPage";
    }

    @RequestMapping(value="/showlogin", method = RequestMethod.GET)
    public String printUser(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        model.addAttribute("username", name);
        return "showlogin";

    }

}
