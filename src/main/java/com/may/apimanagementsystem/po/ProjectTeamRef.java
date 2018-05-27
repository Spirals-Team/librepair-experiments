package com.may.apimanagementsystem.po;

public class ProjectTeamRef {

    private int projectId;
    private int teamId;
    private int creatUserId;
    private int delFlag;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getCreatUserId() {
        return creatUserId;
    }

    public void setCreatUserId(int creatUserId) {
        this.creatUserId = creatUserId;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
