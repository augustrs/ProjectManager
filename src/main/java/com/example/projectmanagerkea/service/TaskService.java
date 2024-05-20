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

    public void createTask(Task task, int subprojectId) {
        try {
            taskRepository.createTask(task, subprojectId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Task findTask(int taskId) {
        try {
            return taskRepository.findTask(taskId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<User> getAssigneesForTask(int taskId) {
        try {
            return taskRepository.getAssigneesForTask(taskId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public String getStatusForTask(int taskId) {
        try {
            return taskRepository.getStatusForTask(taskId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void assignUserToTask(int taskId, int userId) {
        try {
            taskRepository.assignUserToTask(taskId, userId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void unassignUserFromTask(int taskId, int userId) {
        try {
            taskRepository.unassignUserFromTask(taskId, userId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<Task> assignedTasksForUser(int userId) {
        try {
            return taskRepository.assignedTasks(userId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void updateTask(Task updatedTask, int statusId) {
        try {
            taskRepository.updateTask(updatedTask, statusId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void deleteTask(int taskId) {
        try {
            taskRepository.deleteTask(taskId);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
