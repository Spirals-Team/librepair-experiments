package com.gdc.aerodev.model;

/**
 * This entity can be created only by {@code User}. But {@code Cr} can review it. Also any project can be rated by
 * {@code User} and {@code Cr} in future versions.
 *
 * @author Yusupov Danil
 */
public class Project {

    private Long projectId;
    private String projectName;
    private Long projectOwner;
    private ProjectType projectType;
    private String projectDescription;

    public Project() {
    }

    public Project(Long projectId, String projectName, Long projectOwner, ProjectType projectType, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectType = projectType;
        this.projectDescription = projectDescription;
    }

    public Project(String projectName, Long projectOwner, ProjectType projectType, String projectDescription) {
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectType = projectType;
        this.projectDescription = projectDescription;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Long getProjectOwner() {
        return projectOwner;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectOwner(Long projectOwner) {
        this.projectOwner = projectOwner;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
}
