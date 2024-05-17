package com.example.projectmanagerkea.model;

public class Project {
    private int projectId;
    private String projectName;
    private String projectDescription;

    public Project(int projectId, String projectName, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    public Project() {}

    public int getProjectId() {
        return projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

}
