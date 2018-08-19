package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {


    @Autowired
    private UserAccountService accountService;

    @Autowired
    private UserService userService;

    @RequestMapping("/accounts")
    public ModelAndView getAll() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        long userId = userService.getUserByEmail(email).getId();


        ModelAndView mav = new ModelAndView("accounts");
        List<UserAccount> accounts = new ArrayList<>(accountService.getAll(userId));
        mav.addObject("accounts", accounts);

        return mav;
    }

    @RequestMapping("/account/id")
    public ModelAndView getById(@RequestParam long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        long userId = userService.getUserByEmail(email).getId();


        ModelAndView mav = new ModelAndView("account");

        UserAccount account = accountService.getById(id, userId);
        mav.addObject("account", account);

        return mav;
    }

    @RequestMapping("/account/add")
    public ModelAndView addAccount() {

        ModelAndView mav = new ModelAndView("account");

        mav.addObject("user", new UserAccount());

        return mav;
    }

    @RequestMapping(value = "/account/addAccount", method = RequestMethod.POST)
    public String submit(@ModelAttribute("user") UserAccount account, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        long userId = userService.getUserByEmail(email).getId();

        accountService.save(account, userId);

        return "redirect:/accounts";
    }

    @RequestMapping(value = "/account/removeAccount")
    public String remove(@RequestParam("id") long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        long userId = userService.getUserByEmail(email).getId();

        UserAccount u = new UserAccount();
        u.setId(id);
        accountService.remove(u, userId);

        return "redirect:/users";
    }

}
