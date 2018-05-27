package com.may.apimanagementsystem.dao;

import com.may.apimanagementsystem.po.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMapper {


    boolean insertTeam(Team team);

    boolean deleteTeam(int teamId);

    boolean updateTeam(Team team);

    Team findTeamByTeamId(int teamId);

    Team findTeamByTeamName(String teamName);

    List<Team> searchMyTeamsByuserId(int userId);

}
