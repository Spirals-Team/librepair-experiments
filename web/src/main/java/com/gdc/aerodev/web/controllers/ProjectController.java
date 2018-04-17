package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.service.impl.ProjectService;
import com.gdc.aerodev.service.impl.UserService;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController implements LoggingWeb{

    private final ProjectService prj_service;
    private final UserService usr_service;

    public ProjectController(ProjectService prj_service, UserService usr_service) {
        this.prj_service = prj_service;
        this.usr_service = usr_service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/project/{id}")
    public ModelAndView project(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("project");
        Project project = prj_service.getProject(id);
        log.debug("Received project '" + project.getProjectName() + "'.");
        mav.addObject("prj", project);
        mav.addObject("ownerName", usr_service.getUser(project.getProjectOwner()).getUserName());
        return mav;
    }

}
