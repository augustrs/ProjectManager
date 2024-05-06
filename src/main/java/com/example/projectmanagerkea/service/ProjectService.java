package com.example.projectmanagerkea.service;


import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(Project newProject, int managerId) throws SQLException {
        projectRepository.createProject(newProject, managerId);
    }


}
