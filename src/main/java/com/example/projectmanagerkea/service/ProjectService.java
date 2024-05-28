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


    public List<Project> findSubprojectsForProject(int projectId) throws SQLException {
        return projectRepository.findSubprojectsForProject(projectId);
    }

    public List<Task> findTasksForSubProject(int subProjectprojectId) throws SQLException {
        return projectRepository.findTasksForSubProject(subProjectprojectId);
    }

    public Project findSubProject(int subProjectId) throws SQLException {
        return projectRepository.findSubProject(subProjectId);
    }

    public void editSubProject(Project subProject) throws SQLException {
        projectRepository.editSubProject(subProject);
    }

    public void deleteSubProject(int subProjectId) throws SQLException {
        projectRepository.deleteSubProject(subProjectId);
    }

    public void createSubProject(Project newSubProject, int projectId) throws SQLException {
        projectRepository.createSubProject(newSubProject, projectId);
    }
    public void deleteProject(int projectId) throws SQLException {
        projectRepository.deleteProject(projectId);
    }
    public void editProject(Project project) throws SQLException {
        projectRepository.editProject(project);
    }
    public Project findProject(int projectId) throws SQLException {
        return projectRepository.findProject(projectId);
    }
}