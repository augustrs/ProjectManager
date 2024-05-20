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

    public void createProject(Project newProject, int managerId) {
        try {
            projectRepository.createProject(newProject, managerId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<Project> getAllProjects() {
        try {
            return projectRepository.getAllProjects();
        } catch (RuntimeException e) {
            throw e;
        }
    }


    public List<Project> findSubprojectsForProject(int projectId) {
        try {
            return projectRepository.findSubprojectsForProject(projectId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<Task> findTasksForSubProject(int subProjectprojectId) {
        try {
            return projectRepository.findTasksForSubProject(subProjectprojectId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Project findSubProject(int subProjectId) {
        try {
            return projectRepository.findSubProject(subProjectId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void editSubProject(Project subProject) {
        try {
            projectRepository.editSubProject(subProject);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void deleteSubProject(int subProjectId) {
        try {
            projectRepository.deleteSubProject(subProjectId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

}