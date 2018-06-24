package pl.sda.jira.project.model;

import com.sun.corba.se.impl.orb.ParserTable;
import pl.sda.jira.project.model.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamRepository {

    Map<Long,Team> teamMap = new HashMap();

    public void addTeam(Team team){
        teamMap.put(team.getId(),team);
    }

    public void removeTeam(Long id){
        teamMap.remove(id);
    }

    public boolean checkIfTeamExist(Long id){
        return teamMap.containsKey(id);
    }
}
