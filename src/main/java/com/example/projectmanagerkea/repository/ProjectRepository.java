package com.example.projectmanagerkea.repository;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

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

    public void createTask(Task newTask, int projectId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "INSERT INTO task(name, description, project_id) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, newTask.getTaskName());
        ps.setString(2, newTask.getTaskDescription());
        ps.setInt(3, projectId);
        ps.executeUpdate();
    }

}
