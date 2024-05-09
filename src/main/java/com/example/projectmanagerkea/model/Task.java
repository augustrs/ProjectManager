package com.example.projectmanagerkea.model;

public class Task {

    private int taskId;
    private String taskName;
    private String taskDescription;
    private int taskTime;
    private float taskPrice;
    private int taskStatusId;

    public Task(int taskId, String taskName, String taskDescription, int taskTime, float taskPrice, int taskStatusId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
        this.taskPrice = taskPrice;
        this.taskStatusId = taskStatusId;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskPrice(float taskPrice) {
        this.taskPrice = taskPrice;
    }

    public void setTaskStatusId(int taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public int getTaskTime() {
        return taskTime;
    }

    public float getTaskPrice() {
        return taskPrice;
    }

    public int getTaskStatusId() {
        return taskStatusId;
    }

    public Task() {}

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

}