package com.example.projectmanagerkea.service;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(Project newProject, int managerId) throws SQLException {
        projectRepository.createProject(newProject, managerId);
    }
    public List<Project> getAllProjects() throws SQLException {
        return projectRepository.getAllProjects();
    }

    public void createTask(Task task, int subprojectId) throws SQLException {
        projectRepository.createTask(task, subprojectId);
    }

    public List<Project> findSubprojectsForProject(int projectId) throws SQLException {
        return projectRepository.findSubprojectsForProject(projectId);
    }
    public List<Task> findTasksForSubProject(int subProjectprojectId) throws SQLException {
        return projectRepository.findTasksForSubProject(subProjectprojectId);
    }

    public Task findTask(int taskId) throws SQLException {
        return projectRepository.findTask(taskId);
    }
    public List<User> getAssigneesForTask(int taskId) throws SQLException {
        return projectRepository.getAssigneesForTask(taskId);
    }
    public String getStatusForTask(int taskId) throws SQLException {
        return projectRepository.getStatusForTask(taskId);
    }
    public void assignUserToTask(int taskId, int userId) throws SQLException {
        projectRepository.assignUserToTask(taskId, userId);
    }
    public void unassignUserFromTask(int taskId, int userId) throws SQLException {
        projectRepository.unassignUserFromTask(taskId, userId);
    }
}