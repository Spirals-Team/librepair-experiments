package com.may.apimanagementsystem.controller;

import com.may.apimanagementsystem.constant.ReturnCode;
import com.may.apimanagementsystem.dto.ResponseEntity;
import com.may.apimanagementsystem.po.Project;
import com.may.apimanagementsystem.po.ProjectUserRef;
import com.may.apimanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import com.may.apimanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.may.apimanagementsystem.constant.ExceptionMessage.SUCCESS;

@RestController
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> addProject(Project project){
        projectService.addProject(project);
        return new ResponseEntity<>(ReturnCode.SUCCESS_CODE,SUCCESS,null);
    }

//    @PostMapping
//    public ResponseEntity<Project> addProjectByUser(ProjectUserRef projectUserRef){
//        projectService.addProjectByUser(projectUserRef);
//        return new ResponseEntity<>(200,SUCCESS,null);
//    }

    @GetMapping("userProject")
    public ResponseEntity<List<Project>> showProject(int pageNum, int pageSize,int userId){
        List<Project> projects = null;
        try {
            projects = projectService.getList(pageNum,pageSize,userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(ReturnCode.SUCCESS_CODE,SUCCESS,projects);
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<Project> deleteProject(@PathVariable int projectId){
        projectService.removeProject(projectId);
        return new ResponseEntity<>(ReturnCode.SUCCESS_CODE,SUCCESS,null);
    }

    @PutMapping("{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable int projectId, Project project){
        projectService.updateProject(project);
        return new ResponseEntity<>(ReturnCode.SUCCESS_CODE,SUCCESS,null);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<Project> findProjectByProjectId(@PathVariable int projectId,Project project){
        projectService.getProjectByProjectId(projectId);
        return new ResponseEntity<>(ReturnCode.SUCCESS_CODE,SUCCESS,project);
    }

}
