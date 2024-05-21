package com.example.projectmanagerkea.service;

import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.repository.ProjectRepository;
import com.example.projectmanagerkea.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task, int subprojectId) throws SQLException {

            taskRepository.createTask(task, subprojectId);

    }

    public Task findTask(int taskId) throws SQLException {
            return taskRepository.findTask(taskId);
    }

    public List<User> getAssigneesForTask(int taskId) throws SQLException {
            return taskRepository.getAssigneesForTask(taskId);
    }

    public String getStatusForTask(int taskId) throws SQLException {
            return taskRepository.getStatusForTask(taskId);
    }

    public void assignUserToTask(int taskId, int userId) throws SQLException {
            taskRepository.assignUserToTask(taskId, userId);

    }

    public void unassignUserFromTask(int taskId, int userId) throws SQLException {
            taskRepository.unassignUserFromTask(taskId, userId);
    }

    public List<Task> assignedTasksForUser(int userId) throws SQLException {
            return taskRepository.assignedTasks(userId);
    }

    public void updateTask(Task updatedTask, int statusId) throws SQLException {
            taskRepository.updateTask(updatedTask, statusId);
    }

    public void deleteTask(int taskId) throws SQLException {
            taskRepository.deleteTask(taskId);
    }
}
