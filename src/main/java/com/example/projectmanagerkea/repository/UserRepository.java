package com.example.projectmanagerkea.repository;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;

    public User findUser(String username) {

        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM USER WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            User user = null;

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setRealName(rs.getString("real_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("role_id"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user: " + e.getMessage());
        }
    }

    public User verifyUser(String username, String password) {
        try {
            User user = findUser(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void createUser(User newUser) {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "INSERT INTO USER(real_name, username, password, role_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {


            ps.setString(1, newUser.getRealName());
            ps.setString(2, newUser.getUsername());
            ps.setString(3, newUser.getPassword());
            ps.setInt(4, newUser.getRoleId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }


    public List<Project> findManagedProjects(int managerUserId) {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM project WHERE USER_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, managerUserId);
            ResultSet rs = ps.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (rs.next()) {
                Project project = new Project();
                project.setProjectName(rs.getString("name"));
                project.setProjectDescription(rs.getString("description"));
                project.setProjectId(rs.getInt("project_id"));
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding managed projects: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM USER";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setRealName(rs.getString("real_name"));
                user.setUsername(rs.getString("username"));
                user.setUserId(rs.getInt("user_id"));
                user.setRoleId(rs.getInt("role_id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all users: " + e.getMessage());
        }
    }

    public List<User> getAllEmployees() {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM USER WHERE role_id = 3";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setRealName(rs.getString("real_name"));
                user.setUsername(rs.getString("username"));
                user.setUserId(rs.getInt("user_id"));
                user.setRoleId(rs.getInt("role_id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all employees: " + e.getMessage());
        }
    }
}
