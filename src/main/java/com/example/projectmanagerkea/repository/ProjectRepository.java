package com.example.projectmanagerkea.repository;


import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;

    public void createProject(Project newProject, int managerId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "INSERT INTO project(name, description, manager_id) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, newProject.getProjectName());
        ps.setString(2, newProject.getProjectDescription());
        ps.setInt(3, managerId);
        ps.executeUpdate();
    }


    public List<Project> getAllProjects() throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM project";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SQL);
        List<Project> projects = new ArrayList<>();
        while (rs.next()) {
            Project project = new Project();
            project.setProjectName(rs.getString("name"));
            project.setProjectDescription(rs.getString("description"));
            projects.add(project);
        }
        return projects;
    }

}
