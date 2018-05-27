package com.may.apimanagementsystem.dao;


import com.may.apimanagementsystem.po.Project;
import com.may.apimanagementsystem.po.TeamProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamProjectMapper {

    boolean deleteTeamProject(int projectId);

    List<Project> selectTeamProject(int teamId);
}
