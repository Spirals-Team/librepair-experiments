package com.may.apimanagementsystem.controller;


import com.may.apimanagementsystem.dao.TeamProjectMapper;
import com.may.apimanagementsystem.dto.ResponseEntity;
import com.may.apimanagementsystem.po.*;
import com.may.apimanagementsystem.service.TeamMemberServise;
import com.may.apimanagementsystem.service.TeamProjectServise;
import com.may.apimanagementsystem.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.may.apimanagementsystem.constant.ExceptionMessage.SUCCESS;


@RestController
@RequestMapping("team")
public class TeamController {


    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamMemberServise teamMemberServise;
    @Autowired
    private TeamProjectServise teamProjectServise;


    @PostMapping
    public ResponseEntity<Team> createTeam(Team team) {
        teamService.addTeam(team);
        return new ResponseEntity<>(200, SUCCESS, team);
    }

    @DeleteMapping("{teamId}")
    public ResponseEntity<Team> deleteTeam(@PathVariable int teamId) {
        teamService.removeTeam(teamId);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PutMapping("{teamId}")
    public ResponseEntity<Object> updateTeam(@PathVariable int teamId, Team team) {
        teamService.updateTeam(team);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("userTeam")
    public ResponseEntity<List<Team>> showTeamsByuserId(int userId) {
        List<Team> teams = teamService.searchMyTeamByuserId(userId);
        return new ResponseEntity<>(200, SUCCESS, teams);
    }


    /*团队成员*/
    @GetMapping("teamMember")
    public ResponseEntity<List<User>> selectTeamMember(int teamId) {
        List<User> members = teamMemberServise.selectTeamMembers(teamId);
        return new ResponseEntity<>(200, SUCCESS, members);
    }

    @PostMapping("teamMember")
    public ResponseEntity<TeamMember> addTeamMember(TeamMember teamMember) {
        teamMemberServise.addTeamMerber(teamMember);
        return new ResponseEntity<>(200, SUCCESS, teamMember);
    }

    @DeleteMapping("teamMember")
    public ResponseEntity<TeamMember> deleteTeamMember(TeamMember teamMember) {
        teamMemberServise.deleteTeamMember(teamMember);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    /*团队项目*/

    @DeleteMapping("teamProject")
    public ResponseEntity<TeamProject> deleteTeamproject(int projectId) {
        teamProjectServise.removeTeamProject(projectId);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("teamProject")
    public ResponseEntity<List<Project>> selectTeamproject(int teamId) {
        List<Project> projects = teamProjectServise.searchMyTeamByuserId(teamId);
        return new ResponseEntity<>(200, SUCCESS, projects);
    }


}