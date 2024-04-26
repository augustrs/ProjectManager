package com.example.projectmanagerkea.service;


import com.example.projectmanagerkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }












}
