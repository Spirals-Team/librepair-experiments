package com.epam.brest.course.model;

public class Player {

    private Integer player_id;

    private Integer player_number;

    private String player_name;

    private Integer player_age;

    private Integer player_cost;

    private Integer player_team_id;

    public Player(Integer player_number,
                  String player_name, Integer player_age,
                  Integer player_cost, Integer player_team_id) {

        this.player_number = player_number;
        this.player_name = player_name;
        this.player_age = player_age;
        this.player_cost = player_cost;
        this.player_team_id = player_team_id;
    }

    public Player() {

    }

    public Integer getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(Integer player_id) {
        this.player_id = player_id;
    }

    public Integer getPlayer_number() {
        return player_number;
    }

    public void setPlayer_number(Integer player_number) {
        this.player_number = player_number;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public Integer getPlayer_age() {
        return player_age;
    }

    public void setPlayer_age(Integer player_age) {
        this.player_age = player_age;
    }

    public Integer getPlayer_cost() {
        return player_cost;
    }

    public void setPlayer_cost(Integer player_cost) {
        this.player_cost = player_cost;
    }

    public Integer getPlayer_team_id() {
        return player_team_id;
    }

    public void setPlayer_team_id(Integer player_team_id) {
        this.player_team_id = player_team_id;
    }
}
