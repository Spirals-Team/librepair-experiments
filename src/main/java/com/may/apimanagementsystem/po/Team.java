package com.may.apimanagementsystem.po;

public class Team {
    private int teamId;
    private String teamName;
    private String description;
    private int delFlag;
    private int createuserId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getCreateuserId() {
        return createuserId;
    }

    public void setCreateuserId(int createuserId) {
        this.createuserId = createuserId;
    }
}
