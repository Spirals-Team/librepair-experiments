package com.may.apimanagementsystem.dao;

import com.github.pagehelper.Page;
import com.may.apimanagementsystem.po.Project;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProjectMapper {

    List<Project> getProjectList(int userId);

    boolean deleteProject(int projectId);

    boolean insertProject(Project project);

    boolean updateProject(Project project);

    Project findProjectByProjectId(int projectId);

}
