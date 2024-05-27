package com.example.projectmanagerkea.RepositoryTest;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.repository.ProjectRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)



class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;
@Order(1)
    @Test
    void findAllProjects() throws SQLException {
        assertEquals(3, projectRepository.getAllProjects().size());
    }
@Order(2)
    @Test
    void findSubprojectsForProject() throws SQLException {
        List<Project> subprojects = projectRepository.findSubprojectsForProject(1);
        assertEquals(2, subprojects.size());
    }
@Order(3)
    @Test
    void findTasksForSubProject() throws SQLException {
        List<Task> tasks = projectRepository.findTasksForSubProject(1);
        assertEquals(2, tasks.size());
    }
@Order(4)
    @Test
    void findSubProject() throws SQLException {
        Project subProject = projectRepository.findSubProject(1);
        assertEquals("Subproject 1", subProject.getProjectName());
    }
@Order(5)
    @Test
    void createProject() throws SQLException {
        Project newProject = new Project();
        newProject.setProjectName("New Project");
        newProject.setProjectDescription("New Project Description");
        projectRepository.createProject(newProject, 2);

        List<Project> projects = projectRepository.getAllProjects();
        assertEquals(4, projects.size());
    }
@Order(6)
    @Test
    void editSubProject() throws SQLException {
        Project subProject = projectRepository.findSubProject(1);
        subProject.setProjectName("Updated Subproject");
        projectRepository.editSubProject(subProject);

        Project updatedSubProject = projectRepository.findSubProject(1);
        assertEquals("Updated Subproject", updatedSubProject.getProjectName());
    }
@Order(7)
    @Test
    void deleteSubProject() throws SQLException {
        projectRepository.deleteSubProject(1);
        List<Project> subprojects = projectRepository.findSubprojectsForProject(1);
        assertEquals(1, subprojects.size());
    }
}
