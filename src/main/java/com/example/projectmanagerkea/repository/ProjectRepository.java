package com.example.projectmanagerkea.repository;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
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
            project.setProjectId(rs.getInt("project_id"));
            projects.add(project);
        }
        return projects;
    }



    public List<Project> findSubprojectsForProject(int projectId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM subproject WHERE project_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, projectId);
        ResultSet rs = ps.executeQuery();
        List<Project> subprojects = new ArrayList<>();
        while (rs.next()) {
            Project subproject = new Project();
            subproject.setProjectName(rs.getString("name"));
            subproject.setProjectDescription(rs.getString("description"));
            subproject.setProjectId(rs.getInt("subproject_id"));
            subprojects.add(subproject);
        }
        return subprojects;
    }
    public List<Task> findTasksForSubProject(int subProjectprojectId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM task WHERE subproject_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, subProjectprojectId);
        ResultSet rs = ps.executeQuery();
        List<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            Task task = new Task();
            task.setTaskName(rs.getString("task_name"));
            task.setTaskDescription(rs.getString("task_description"));
            task.setTaskId(rs.getInt("task_id"));
            task.setTaskTime(rs.getInt("time"));
            task.setTaskPrice(rs.getFloat("price"));
            task.setTaskStatusId(rs.getInt("status_id"));
            tasks.add(task);

        }
        return tasks;
    }


    public Task findTask(int taskId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM task WHERE task_id = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            Task task = new Task();
            if (rs.next()) {
                task.setTaskName(rs.getString("task_name"));
                task.setTaskDescription(rs.getString("task_description"));
                task.setTaskId(rs.getInt("task_id"));
                task.setTaskTime(rs.getInt("time"));
                task.setTaskPrice(rs.getFloat("price"));
                task.setTaskStatusId(rs.getInt("status_id"));
            }
            return task;

    }
    public List<User> getAssigneesForTask(int taskId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT u.real_name, u.user_id " +
                "FROM EMPLOYEE_TASK et " +
                "JOIN USER u ON et.user_id = u.user_id " +
                "WHERE et.task_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, taskId);
        ResultSet rs = ps.executeQuery();
        List<User> assignees = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setRealName(rs.getString("real_name"));
            user.setUserId(rs.getInt("user_id"));
            assignees.add(user);
        }
        return assignees;
    }


    public void createTask(Task newTask, int subprojectId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "INSERT INTO task(task_name, task_description, time, price ,status_id, subproject_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, newTask.getTaskName());
        ps.setString(2, newTask.getTaskDescription());
        ps.setInt(3, newTask.getTaskTime());
        ps.setFloat(4, newTask.getTaskPrice());
        ps.setInt(5, 1); // status_id 1 is for "Not started"
        ps.setInt(6, subprojectId);
        ps.executeUpdate();
    }
    public String getStatusForTask(int taskId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT s.status FROM TASK t JOIN STATUS s ON t.status_id = s.status_id WHERE t.task_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, taskId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("status");
        }
        return null;
    }

    public void assignUserToTask(int taskId, int userId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "INSERT INTO employee_task(user_id, task_id) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, userId);
        ps.setInt(2, taskId);
        ps.executeUpdate();
    }
    public void unassignUserFromTask(int taskId, int userId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "DELETE FROM employee_task WHERE user_id = ? AND task_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, userId);
        ps.setInt(2, taskId);
        ps.executeUpdate();
    }

    public void updateTask(Task updatedTask) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "UPDATE TASK SET task_name = ?, task_description = ?, time = ?, price = ? WHERE task_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);

        ps.setString(1, updatedTask.getTaskName());
        ps.setString(2, updatedTask.getTaskDescription());
        ps.setInt(3, updatedTask.getTaskTime());
        ps.setFloat(4, updatedTask.getTaskPrice());
        ps.setInt(5, updatedTask.getTaskId());

        ps.executeUpdate();
    }
}
