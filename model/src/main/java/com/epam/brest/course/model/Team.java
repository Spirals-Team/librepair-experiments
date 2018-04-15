package com.epam.brest.course.model;

public class Team {

    private Integer team_id;

    private String team_name;

    private String team_country;

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_country() {
        return team_country;
    }

    public void setTeam_country(String team_country) {
        this.team_country = team_country;
    }

    public Team() {

    }

    public Team(Integer team_id, String team_name, String team_country) {

        this.team_id = team_id;
        this.team_name = team_name;
        this.team_country = team_country;
    }
}
