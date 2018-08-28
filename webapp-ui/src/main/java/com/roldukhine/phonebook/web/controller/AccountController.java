package com.roldukhine.phonebook.web.controller;

import com.roldukhine.entity.User;
import com.roldukhine.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String username,
                              @RequestParam String password,
                              String rememberMe,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        logger.debug("/login login {}, password {}");

        ModelAndView modelAndView = new ModelAndView("login");

        User checkUser = userService.checkUser(username, password);
        logger.debug("checkUser: {}", checkUser);
        if (checkUser == null) {
            logger.debug("return view {}", modelAndView);
            return modelAndView;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            Cookie cookie = new Cookie("username", username);
            Cookie cookiePass = new Cookie("password", password);
            boolean rememberBeOn = "on".equals(rememberMe);
            logger.debug("rememberBeOn {}", rememberBeOn);
            if (rememberBeOn) {
                cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(14));
                cookiePass.setMaxAge((int) TimeUnit.DAYS.toSeconds(14));
                cookie.setPath("/");
                cookiePass.setPath("/");
            }
            response.addCookie(cookie);
            response.addCookie(cookiePass);
        }
        String redirectAddress = "redirect:/employee/getAll";
        logger.debug("return view {}", redirectAddress);
        return new ModelAndView(redirectAddress);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        logger.debug("/login");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("/logout");

        HttpSession session = request.getSession(false);
        session.setAttribute("user", null);
        session.invalidate();
        logger.debug("invalidate session");

        int maxAgeCookie = 0;
        String pathToCookie = "/";

        Cookie cookie = new Cookie("login", null);
        Cookie cookiePass = new Cookie("password", null);
        cookie.setMaxAge(maxAgeCookie);
        cookie.setPath(pathToCookie);
        cookiePass.setMaxAge(maxAgeCookie);
        cookiePass.setPath(pathToCookie);

        response.addCookie(cookie);
        response.addCookie(cookiePass);

        String redirectAddress = "redirect:/account/login";
        logger.debug("redirect view {}", redirectAddress);
        return redirectAddress;
    }
}
