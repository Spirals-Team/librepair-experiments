package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.impl.ProjectService;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CreateProjectController implements LoggingWeb{

    private final ProjectService prjService;

    public CreateProjectController(ProjectService prjService) {
        this.prjService = prjService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/create_prj")
    public ModelAndView getPage(HttpSession session){
        User user = (User) session.getAttribute("client");
        log.debug("Received user '" + user.getUserName() + "'.");
        ModelAndView mav = new ModelAndView("create_prj");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create_prj")
    public String createProject(HttpServletRequest request){
        Long owner = Long.valueOf(request.getParameter("usrId"));
        Long id = prjService.createProject(request.getParameter("name"),
                owner,
                ProjectType.valueOf(request.getParameter("type").toUpperCase()),
                request.getParameter("description"));
        if  (id != null){
            log.info("Created project with id " + id + ". Owner id " + owner + ".");
            return "redirect:/profile";
        } else {
            log.error("Cannot create project for user (id " + owner + ").");
            return "redirect:/create_prj?error";
        }
    }

}
