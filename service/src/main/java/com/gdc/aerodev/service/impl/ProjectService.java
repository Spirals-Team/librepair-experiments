package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.GenericProjectService;
import com.gdc.aerodev.service.logging.LoggingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements GenericProjectService, LoggingService {

    private final PostgresProjectDao dao;

    public ProjectService(PostgresProjectDao dao) {
        this.dao = dao;
    }

    @Override
    public Long createProject(String projectName, Long projectOwner, ProjectType projectType, String projectDescription) {
        if (projectName.equals("") || projectOwner == null || projectDescription.equals("")) {
            return null;
        }
        if (isExistentName(projectName)) {
            log.error("Project with name '" + projectName + "' is already exists.");
            return null;
        }
        try {
            Long id = dao.save(new Project(projectName, projectOwner, projectType, projectDescription));
            log.info("Project '" + projectName + "' created with id " + id + ".");
            return id;
        } catch (DaoException e) {
            return null;
        }
    }

    @Override
    public Long updateProject(Long projectId, String projectName, ProjectType projectType, String projectDescription) {
        Project project = dao.getById(projectId);
        if (!projectName.equals("")) {
            if (isExistentName(projectName)) {
                log.error("Project with name '" + projectName + "' is already exists.");
                return null;
            }
            project.setProjectName(projectName);
        } else if (projectDescription.equals("")){
            return null;
        }
        if (!projectDescription.equals("")){
            project.setProjectDescription(projectDescription);
        }
        project.setProjectType(projectType);
        try{
            log.info("Project '" + projectName + "' successfully updated.");
            return dao.save(project);
        } catch (DaoException e){
            return null;
        }
    }

    @Override
    public Project getProject(String name) {
        return dao.getByName(name);
    }

    @Override
    public Project getProject(Long id) {
        return dao.getById(id);
    }

    public List<Project> getByUserId(Long id){
        return dao.getByUserId(id);
    }

    public int countProjects(){
        return dao.count();
    }

    private boolean isExistentName(String projectName) {
        return dao.getByName(projectName) != null;
    }

    public List<Project> getTopThree() {
        return dao.getTopThree();
    }
}
